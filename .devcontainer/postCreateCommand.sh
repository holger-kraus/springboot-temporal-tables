#!/bin/bash

echo "🚀 Setting up development environment..."

# Create Claude config directory
echo "📝 Configuring Claude API authentication..."
mkdir -p ~/.config/claude

# Create auth.json with the secret if CLAUDE_API_KEY is set
if [ -n "${CLAUDE_API_KEY}" ]; then
    cat > ~/.config/claude/auth.json << EOF
{
  "api_key": "${CLAUDE_API_KEY}"
}
EOF
    chmod 600 ~/.config/claude/auth.json
    echo "✅ Claude API authentication configured successfully"
else
    echo "⚠️  Warning: CLAUDE_API_KEY not set in Codespace secrets"
    echo "   Please add CLAUDE_API_KEY to your Codespace secrets:"
    echo "   https://github.com/settings/codespaces"
fi

# Update package lists
echo "📦 Updating package lists..."
sudo apt-get update -qq

# Install additional useful tools
echo "🛠️  Installing additional development tools..."
sudo apt-get install -y -qq \
    jq \
    httpie \
    htop \
    tree \
    bat \
    ripgrep

# Set up Git (optional, remove if not needed)
if [ -n "${GIT_USER_NAME}" ] && [ -n "${GIT_USER_EMAIL}" ]; then
    echo "🔧 Configuring Git..."
    git config --global user.name "${GIT_USER_NAME}"
    git config --global user.email "${GIT_USER_EMAIL}"
    echo "✅ Git configured"
fi

# Create common project directories
echo "📁 Creating project structure..."
mkdir -p ~/workspace/{src,lib,docs,scripts}

# Install global npm packages (optional)
echo "📦 Installing global npm packages..."
npm install -g nodemon prettier eslint

# Install Claude Code CLI
echo "🤖 Installing Claude Code..."
if [ -n "${CLAUDE_API_KEY}" ]; then
    # Download and install Claude Code
    curl -fsSL https://raw.githubusercontent.com/anthropics/claude-code/main/install.sh | sh

    # Verify installation
    if command -v claude &> /dev/null; then
        echo "✅ Claude Code installed successfully"

        # Configure Claude Code with API key
        export ANTHROPIC_API_KEY="${CLAUDE_API_KEY}"
        echo "export ANTHROPIC_API_KEY=\"${CLAUDE_API_KEY}\"" >> ~/.bashrc
        echo "✅ Claude Code configured with API key"

        # Show version
        claude --version
    else
        echo "⚠️  Warning: Claude Code installation failed"
        echo "   You can try installing manually with:"
        echo "   curl -fsSL https://raw.githubusercontent.com/anthropics/claude-code/main/install.sh | sh"
    fi
else
    echo "⚠️  Skipping Claude Code installation (CLAUDE_API_KEY not set)"
    echo "   Claude Code requires an API key to function properly"
fi

# Java-specific setup
echo "☕ Configuring Java environment..."

# Create a sample Maven settings.xml if it doesn't exist
if [ ! -f ~/.m2/settings.xml ]; then
    mkdir -p ~/.m2
    cat > ~/.m2/settings.xml << 'EOF'
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <activeProfiles>
        <activeProfile>default</activeProfile>
    </activeProfiles>
    <profiles>
        <profile>
            <id>default</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>https://repo.maven.apache.org/maven2</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>
</settings>
EOF
    echo "✅ Maven settings configured"
fi

# Create a gradle.properties file for better performance
mkdir -p ~/.gradle
cat > ~/.gradle/gradle.properties << 'EOF'
# Gradle performance improvements
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
EOF

# Install SDKMAN (optional - for managing multiple Java versions)
# echo "🔧 Installing SDKMAN..."
# curl -s "https://get.sdkman.io" | bash
# source "$HOME/.sdkman/bin/sdkman-init.sh"

# Final message
echo ""
echo "✨ Development environment setup complete!"
echo ""
echo "Java Version:"
java -version
echo ""
echo "Maven Version:"
mvn -version
echo ""
echo "Gradle Version:"
gradle -version
echo ""

# Check Claude setup
if [ -f ~/.config/claude/auth.json ]; then
    echo "✅ Claude API: Configured"
else
    echo "❌ Claude API: Not configured (missing CLAUDE_API_KEY secret)"
fi

# Check Claude Code
if command -v claude &> /dev/null; then
    echo "✅ Claude Code: Installed ($(claude --version 2>/dev/null | head -n1 || echo 'version check failed'))"
else
    echo "❌ Claude Code: Not installed"
fi

echo ""
echo "Happy coding! 🎉"

# Tips for using Claude Code
if command -v claude &> /dev/null && [ -n "${CLAUDE_API_KEY}" ]; then
    echo ""
    echo "💡 Claude Code Quick Start:"
    echo "   - Ask Claude to write code: claude 'write a hello world in Java'"
    echo "   - Edit a file: claude edit MyClass.java"
    echo "   - Ask about your project: claude 'explain this codebase'"
    echo "   - Get help: claude --help"
    echo ""
    echo "   More info: https://docs.anthropic.com/en/docs/claude-code"
fi