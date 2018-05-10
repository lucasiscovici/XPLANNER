 COLOR_NO='\033[0m' # No Color
 COLOR_WHITE='\033[37m'
 COLOR_BLACK='\033[30m'
 COLOR_BLUE='\033[34m'
 COLOR_LIGHT_BLUE='\033[34m'
 COLOR_GREEN='\033[32m'
 COLOR_LIGHT_GREEN='\033[32m'
 COLOR_CYAN='\033[36m'
 COLOR_LIGHT_CYAN='\033[36m'
 COLOR_RED='\033[31m'
 COLOR_LIGHT_RED='\033[31m'
 COLOR_PURPLE='\033[35m'
 COLOR_LIGHT_PURPLE='\033[35m'
 COLOR_BROWN='\033[33m'
 COLOR_YELLOW='\033[34m'
 COLOR_GRAY='\033[30m'
 COLOR_LIGHT_GRAY='\033[37m'

_cf(){
	echo "$1""$COLOR_NO"
}
color_red(){
	echo `_cf "${COLOR_RED}${1}"`
}
color_blue(){
	echo $(_cf "$COLOR_BLUE$1")
}
color_white(){
	echo $(_cf "$COLOR_WHITE$1")
}
color_black(){
	echo $(_cf "$COLOR_BLACK$1")
}
color_yellow(){
	echo $(_cf "$COLOR_YELLOW$1")
}
color_gray(){
	echo $(_cf "$COLOR_GRAY$1")
}
color_brown(){
	echo $(_cf "$COLOR_BROWN$1")
}
color_green(){
	echo $(_cf "$COLOR_GREEN$1")
}