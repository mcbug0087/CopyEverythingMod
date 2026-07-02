#!/usr/bin/env bash

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        JAVA_CMD="$JAVA_HOME/jre/sh/java"
    else
        JAVA_CMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVA_CMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVA_CMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Increase the maximum file descriptors if we can.
if [ "$cygwin" = "false" -a "$darwin" = "false" -a "$nonstop" = "false" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ "$MAX_FD" = "maximum" -o "$MAX_FD" -ge "$MAX_FD_LIMIT" ] ; then
            MAX_FD="$MAX_FD_LIMIT"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn "Could not set maximum file descriptors: $MAX_FD"
        fi
    else
        warn "Could not query maximum file descriptors: $MAX_FD"
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if $darwin; then
    GRADLE_OPTS="$GRADLE_OPTS \"-Xdock:name=$APP_NAME\" \"-Xdock:icon=$APP_HOME/media/gradle.icns\""
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin ; then
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`
    JAVA_HOME=`cygpath --path --mixed "$JAVA_HOME"`
    JDK_HOME=`cygpath --path --mixed "$JDK_HOME"`
    CYGPATH="`which cygpath`"
    if [ -n "$CYGWIN_HOME" ] ; then
        CYGWIN_HOME=`cygpath --path --mixed "$CYGWIN_HOME"`
    fi
    APP_HOME=`cygpath --path --mixed "$APP_HOME"`
    CLASSPATH=`cygpath --path --mixed "$CLASSPATH"`
fi

# Split up the JVM_OPTS And GRADLE_OPTS values into an array, following the shell quoting and substitution rules
function splitJvmOpts() {
    local IFS=$' \t\n'
    local quoted=""
    local jvmOptsArray=()
    for opt in $*; do
        if [[ -z "$quoted" ]]; then
            if [[ "$opt" =~ ^\' ]]; then
                if [[ "$opt" =~ \'$ ]]; then
                    jvmOptsArray+=("${opt:1:-1}")
                else
                    quoted="$opt"
                fi
            elif [[ "$opt" =~ ^\" ]]; then
                if [[ "$opt" =~ \"$ ]]; then
                    jvmOptsArray+=("${opt:1:-1}")
                else
                    quoted="$opt"
                fi
            else
                jvmOptsArray+=("$opt")
            fi
        else
            quoted="$quoted $opt"
            if [[ "$quoted" =~ (^[^'\"]*[\'\"])[^'\"]*([\'\"]$) ]]; then
                jvmOptsArray+=("${quoted:1:-1}")
                quoted=""
            fi
        fi
    done
    echo "${jvmOptsArray[@]}"
}

# Escape application args
function escapeApplicationArgs() {
    local args=""
    for arg in "$@"; do
        if [[ -n "$args" ]]; then
            args="$args "
        fi
        args="$args\"$arg\""
    done
    echo "$args"
}

# Collect all arguments for the java command, following the shell quoting and substitution rules
DEFAULT_JVM_OPTS=`splitJvmOpts $DEFAULT_JVM_OPTS`
JAVA_OPTS=`splitJvmOpts $JAVA_OPTS`
GRADLE_OPTS=`splitJvmOpts $GRADLE_OPTS`

# Define the command
exec "$JAVA_CMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \
    -Dorg.gradle.appname="$APP_BASE_NAME" \
    -classpath "$CLASSPATH" \
    org.gradle.wrapper.GradleWrapperMain "$@"