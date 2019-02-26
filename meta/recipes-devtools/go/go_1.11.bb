require go-${PV}.inc
require go-target.inc

export GOBUILDMODE=""

SRC_URI += "\
    file://CVE-2018-16874.patch \
    file://CVE-2018-16875.patch \
    file://CVE-2018-16873.patch \
    file://CVE-2019-6486.patch \
"

# Add pie to GOBUILDMODE to satisfy "textrel" QA checking, but mips
# doesn't support -buildmode=pie, so skip the QA checking for mips and its
# variants.
python() {
    if 'mips' in d.getVar('TARGET_ARCH'):
        d.appendVar('INSANE_SKIP_%s' % d.getVar('PN'), " textrel")
    else:
        d.setVar('GOBUILDMODE', 'pie')
}
