require sysstat.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=a23a74b3f4caf9616230789d94217acb"

SRC_URI += "file://0001-Include-needed-headers-explicitly.patch \
            file://0001-sar-Fortify-remap_struct-function.patch \
            file://0001-sar-Update-remap_struct-function-prototype.patch \
            file://0001-sadf-Make-it-more-robust-to-corrupted-datafiles.patch \
            file://CVE-2018-19416-CVE-2018-19517.patch \
"

SRC_URI[md5sum] = "421f958db80e67a27eda1ff6b8ebcdeb"
SRC_URI[sha256sum] = "a96265a22784c29888669f961a0896841d177853f8f057efe063e891962b9090"
