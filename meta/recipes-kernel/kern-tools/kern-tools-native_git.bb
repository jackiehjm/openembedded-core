SUMMARY = "Tools for managing Yocto Project style branched kernels"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://tools/kgit;beginline=5;endline=9;md5=a6c2fa8aef1bda400e2828845ba0d06c"

DEPENDS = "git-native"

SRCREV = "d6529f86fc5bcb3514953ff9fa2f51a3fbf03a0f"
PR = "r12"
PV = "0.2+git${SRCPV}"

inherit native

SRC_URI = "git://git.yoctoproject.org/yocto-kernel-tools.git \
           file://0001-tool-kconf_check-modify-grep-pattern.patch \
"

S = "${WORKDIR}/git"
UPSTREAM_CHECK_COMMITS = "1"

do_compile() { 
	:
}

do_install() {
	cd ${S}
	make DESTDIR=${D}${bindir} install
}

FILESEXTRAPATH = "${THISDIR}/files"
