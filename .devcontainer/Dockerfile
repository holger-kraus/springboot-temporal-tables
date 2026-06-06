FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

# Kaputte Yarn-APT-Quelle entfernen: der hinterlegte Signaturschlüssel
# (NO_PUBKEY 62D54FD4003F6525) ist abgelaufen und lässt sonst jedes
# apt-get update der Features (z. B. sshd) mit Exit-Code 100 scheitern.
RUN rm -f /etc/apt/sources.list.d/yarn.list