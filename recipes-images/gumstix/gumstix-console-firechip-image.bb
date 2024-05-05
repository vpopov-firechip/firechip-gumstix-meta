DESCRIPTION = "A basic console image for Gumstix boards."
LICENSE = "MIT"

IMAGE_FEATURES += "splash ssh-server-openssh"
IMAGE_FEATURES += "package-management debug-tweaks"
# Uncomment below to include dev tools and packages
# IMAGE_FEATURES += "tools-sdk dev-pkgs"

IMAGE_LINGUAS = "en-us"

inherit core-image

# Gumstix machines individually RDEPEND on the firware they need but we repeat
# it here as we might want to use the same image on multiple different machines.
FIRMWARE_INSTALL = " \
    linux-firmware-sd8686 \
    linux-firmware-sd8787 \
    linux-firmware-wl18xx \
    wl18xx-fw \
"

SYSTEM_TOOLS_INSTALL = " \
    alsa-utils \
    bluez-alsa \
    cpufrequtils \
    tzdata \
    rtk-hciattach \
"

DEV_TOOLS_INSTALL = " \
    e2fsprogs-resize2fs \
    memtester \
    mtd-utils-ubifs \
    u-boot-mkimage \
"

NETWORK_TOOLS_INSTALL = " \
    curl \
    dnsmasq \
    hostapd \
    ethtool \
    iproute2 \
    iputils \
    iw \
    ntp \
"

MEDIA_TOOLS_INSTALL = " \
    media-ctl \
    raw2rgbpnm \
    v4l-utils \
    yavta \
"

GRAPHICS_LIBS = " \
    mtdev \ 
    tslib \
"

UTILITIES_INSTALL = " \
    coreutils \
    diffutils \
    dtc \
    findutils \
    gpsd \
    grep \
    gzip \
    less \
    nano \
    sudo \
    tar \
    vim \
    wget \
    zip \
    python \
    python3 \
    python3-pip \
    bzip2 \
    git \
    git-perltools \
    resize-rootfs \
"

IMAGE_INSTALL += " \
    ${FIRMWARE_INSTALL} \
    ${SYSTEM_TOOLS_INSTALL} \
    ${DEV_TOOLS_INSTALL} \
    ${NETWORK_TOOLS_INSTALL} \
    ${MEDIA_TOOLS_INSTALL} \
    ${GRAPHICS_LIBS} \
    ${UTILITIES_INSTALL} \
"

IMAGE_INSTALL_append_rpi += " \
    dpkg \
    apt \
    userland \
    python3-rpi-ws281x \
"

ROOTFS_CMD ?= ""
ROOTFS_POSTPROCESS_COMMAND += "${ROOTFS_CMD}"

# To speed up boot time for devices that do not have a wireless
# network adapter on-board by default, remove the wpa service
# for the wireless adapter
remove_wpa() {
    rm -rf ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}

# Create a generic 'gumstix' user account, part of the gumstix group,
# using '/bin/sh' and with a home directory '/home/gumstix' (see
# /etc/default/useradd).  We set the password to 'gumstix' and add them
# to the 'sudo' group.
inherit extrausers
EXTRA_USERS_PARAMS = " \
    useradd -P gumstix -G sudo gumstix; \
"
