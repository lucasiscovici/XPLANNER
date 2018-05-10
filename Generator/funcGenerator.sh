DIROG="$(dirname $0)/"
template(){
	echo $DIROG"$template$1.txt"
}

baseChange(){
	for (( i = 0; i < ${#bail[@]}; i++ )); do
		changeMoustache "${bail[$i]}" "$(replace '\/' '\.' ${confi[$i]})" "$1" $3
		if [[ $# -ge 2 ]]; then
			name="$2"
			if [[ "${ext[$i]}" != "LUCUXNO" ]]; then
				changeMoustache "${bail[$i]}Name" "$name${ext[$i]}" "$1" $3
				changeMoustache "${bail[$i]}NameV" $(fLower "$name${ext[$i]}") "$1" $3
			fi
		fi
	done
}
speciaux(){
	fil="$1"
	qui="$2"
	if [[ "$qui" == "ctrl" ]]; then
		if [[ "$addRep" == "TRUE" ]]; then
			okMoustache "rep" "$fil" "T"
		else
			nokMoustache "rep" "$fil" "F"
			nokMoustache "rep" "$fil" "T"
		fi
	fi
}
xxxT(){
	local filen="$2"
	local tmp="${2}.tmp"
	local template=$(template "$1")
	local s="$5"
	if cfe "$template" 4;then
		# printD "$( color_green \"$template' Exist'\")" $4
		if [[ "$s" == "TRUE" ]]; then
			printD "$( color_red \'Supprimé $tmp $filen\')"
			rm -rf "$tmp" 2>/dev/null
			rm -rf "$filen" 2>/dev/null
			continue
		fi
		cat $template > $tmp
		baseChange $tmp "$3" $4
		speciaux "$tmp" "$1"
		mv "$tmp" "$2"
		rm -rf "$tmp"
	else
		printD "$( color_red \"$template' Not Exist'\")" $4
	fi
}

