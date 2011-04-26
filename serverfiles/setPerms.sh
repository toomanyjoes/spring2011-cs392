#!/bin/bash

# give execute permissions to jdk
chmod o+rx -R jdk1.7.0

# give execute permissions to bin webGuiDsl lib compose.cgi gpl.cgi
chmod o+rx -R bin webGuiDsl lib compose.cgi webguidsl.cgi

# give read permissions to everything else we need
chmod o+r -R css _debug.cnf gpl.html gpl.m jak/ js/

# Make tmp directory world writable. This is not ideal but we need
# to make files during composition and everything is run under the
# www user.
chmod o+rwx -R tmp/

# hide this script
chmod go-rwx setPerms.sh
