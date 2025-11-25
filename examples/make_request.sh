set -eu

TO=0.300
DUR_S=30
WL_MAX=5000

# UP 300ms = make GET request to https://httpbin.org/get

on_up() {
    read -t $TO key && {
        cmd volume music up
        return
    }
    cmd vibrate $DUR_S
    cmd request GET https://httpbin.org/get
}


loop() {
    cmd permission TERMUX_RUN_COMMAND

    while read key; do
        cmd wakelock acquire $WL_MAX

        case "$key" in
            "$PRESS_UP" )
                on_up
            ;;
            "$PRESS_DOWN" )
                cmd volume music down
            ;;
        esac
        cmd wakelock release
    done
}

encode_list() {
    ENCODED=""; for arg in "$@"; do
        ENCODED="${ENCODED}${#arg}:${arg}"
    done
    ENCODED="${#ENCODED}:$ENCODED"
}
cmd() {
    encode_list "$@";
    echo "$ENCODED"
}

loop
