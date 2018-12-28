SUMMARY = "A low level cryptographic library"
HOMEPAGE = "http://www.lysator.liu.se/~nisse/nettle/"
SECTION = "libs"
LICENSE = "LGPLv3+ | GPLv2+"

LIC_FILES_CHKSUM = "file://COPYING.LESSERv3;md5=6a6a8e020838b23406c81b19c1d46df6 \
                    file://COPYINGv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://serpent-decrypt.c;beginline=14;endline=36;md5=ca0d220bc413e1842ecc507690ce416e \
                    file://serpent-set-key.c;beginline=14;endline=36;md5=ca0d220bc413e1842ecc507690ce416e"

DEPENDS += "gmp"

SRC_URI = "${GNU_MIRROR}/${BPN}/${BP}.tar.gz \
           file://Add-target-to-only-build-tests-not-run-them.patch \
           file://run-ptest \
           file://check-header-files-of-openssl-only-if-enable_.patch \
           file://0001-Add-convenience-macro-for-size-calculation.patch \
           file://0001-Add-side-channel-silent-RSA-root-function.patch \
           file://0002-Use-side-channel-silent-root-for-rsa-signatures.patch \
           file://0003-Add-a-side-channel-silent-conditional-memcpy.patch \
           file://0004-Add-side-channel-silent-pkcs1-decoding-function.patch \
           file://0005-Add-mpn_get_base256.patch \
           file://0006-Unit-test-for-pkcs1-sec-decrypt.patch \
           file://0007-Add-rsa_sec_decrypt-as-side-channel-silent-variant.patch \
           file://0008-Unit-test-for-rsa_sec_decyrpt.patch \
           file://0009-Add-variable-len-pkcs1-sec-decoding-function.patch \
           file://0010-Use-side-channel-silent-pkcs1-in-rsa_decrypt_tr.patch \
           file://0011-Catch-bad-private-keys-early-on.patch \
           file://0012-Randomzed-testing-of-rsa-sec-compute-root.patch \
           file://0013-Switch-rsa_compute_root-to-use-side-channel-safe-var.patch \
           "

SRC_URI_append_class-target = "\
            file://dlopen-test.patch \
            "

SRC_URI[md5sum] = "dc0f13028264992f58e67b4e8915f53d"
SRC_URI[sha256sum] = "ae7a42df026550b85daca8389b6a60ba6313b0567f374392e54918588a411e94"

UPSTREAM_CHECK_REGEX = "nettle-(?P<pver>\d+(\.\d+)+)\.tar"

inherit autotools ptest multilib_header

EXTRA_AUTORECONF += "--exclude=aclocal"

EXTRA_OECONF = "--disable-openssl"

CFLAGS_append = " -std=c99"

do_compile_ptest() {
        oe_runmake buildtest
}

do_install_append() {
    oe_multilib_header nettle/nettle-stdint.h nettle/version.h
}

do_install_ptest() {
        install -d ${D}${PTEST_PATH}/testsuite/
        install ${S}/testsuite/gold-bug.txt ${D}${PTEST_PATH}/testsuite/
        install ${S}/testsuite/*-test ${D}${PTEST_PATH}/testsuite/
        # tools can be found in PATH, not in ../tools/
        sed -i -e 's|../tools/||' ${D}${PTEST_PATH}/testsuite/*-test
        install ${B}/testsuite/*-test ${D}${PTEST_PATH}/testsuite/
}

BBCLASSEXTEND = "native nativesdk"
