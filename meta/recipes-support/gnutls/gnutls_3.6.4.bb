SUMMARY = "GNU Transport Layer Security Library"
HOMEPAGE = "http://www.gnu.org/software/gnutls/"
BUGTRACKER = "https://savannah.gnu.org/support/?group=gnutls"

LICENSE = "GPLv3+ & LGPLv2.1+"
LICENSE_${PN} = "LGPLv2.1+"
LICENSE_${PN}-xx = "LGPLv2.1+"
LICENSE_${PN}-bin = "GPLv3+"
LICENSE_${PN}-openssl = "GPLv3+"

LIC_FILES_CHKSUM = "file://LICENSE;md5=71391c8e0c1cfe68077e7fce3b586283 \
                    file://doc/COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://doc/COPYING.LESSER;md5=a6f89e2100d9b6cdffcea4f398e37343"

DEPENDS = "nettle gmp virtual/libiconv libunistring"
DEPENDS_append_libc-musl = " argp-standalone"

SHRT_VER = "${@d.getVar('PV').split('.')[0]}.${@d.getVar('PV').split('.')[1]}"

SRC_URI = "https://www.gnupg.org/ftp/gcrypt/gnutls/v${SHRT_VER}/gnutls-${PV}.tar.xz \
           file://arm_eabi.patch \
           file://CVE-2018-16868.patch \
           file://CVE-2019-3829.patch \
           file://005eb5cbad48e22a4b0c36cd97f1c0225f3eed7f \
           file://c2632449b011340199af11389c073d2d380b2e1e \
           file://cacdb69aaf394120d761291f43983336d15c7be3 \
           file://cve-2019-3829.pem \
           file://CVE-2019-3836.patch \
"

SRC_URI[md5sum] = "63363d1c00601f4d11a5cadc8b5e0799"
SRC_URI[sha256sum] = "c663a792fbc84349c27c36059181f2ca86c9442e75ee8b0ad72f5f9b35deab3a"

inherit autotools texinfo binconfig pkgconfig gettext lib_package gtk-doc

PACKAGECONFIG ??= "libidn"

# You must also have CONFIG_SECCOMP enabled in the kernel for
# seccomp to work.
PACKAGECONFIG[seccomp] = "ac_cv_libseccomp=yes,ac_cv_libseccomp=no,libseccomp"
PACKAGECONFIG[libidn] = "--with-idn,--without-idn,libidn2"
PACKAGECONFIG[libtasn1] = "--with-included-libtasn1=no,--with-included-libtasn1,libtasn1"
PACKAGECONFIG[p11-kit] = "--with-p11-kit,--without-p11-kit,p11-kit"
PACKAGECONFIG[tpm] = "--with-tpm,--without-tpm,trousers"

EXTRA_OECONF = " \
    --enable-doc \
    --disable-libdane \
    --disable-guile \
    --disable-rpath \
    --enable-local-libopts \
    --enable-openssl-compatibility \
    --with-libpthread-prefix=${STAGING_DIR_HOST}${prefix} \
    --without-libunistring-prefix \
"

LDFLAGS_append_libc-musl = " -largp"

do_configure_prepend() {
	for dir in . lib; do
		rm -f ${dir}/aclocal.m4 ${dir}/m4/libtool.m4 ${dir}/m4/lt*.m4
	done
}

do_git_apply () {
	cd ${S}
	mkdir -p fuzz/gnutls_x509_verify_fuzzer.in
	cp ${S}/../c2632449b011340199af11389c073d2d380b2e1e fuzz/gnutls_x509_verify_fuzzer.in
	cp ${S}/../005eb5cbad48e22a4b0c36cd97f1c0225f3eed7f fuzz/gnutls_x509_verify_fuzzer.in
	cp ${S}/../cacdb69aaf394120d761291f43983336d15c7be3 fuzz/gnutls_x509_verify_fuzzer.repro
	cp ${S}/../cve-2019-3829.pem tests/cert-tests/data/
}

do_patch_append() {
    bb.build.exec_func('do_git_apply', d)
}

PACKAGES =+ "${PN}-openssl ${PN}-xx"

FILES_${PN}-dev += "${bindir}/gnutls-cli-debug"
FILES_${PN}-openssl = "${libdir}/libgnutls-openssl.so.*"
FILES_${PN}-xx = "${libdir}/libgnutlsxx.so.*"

BBCLASSEXTEND = "native nativesdk"
