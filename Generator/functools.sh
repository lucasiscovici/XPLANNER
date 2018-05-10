toNum(){
	if $1; then
		return 1
	else
		return 0
	fi

}

printD(){
	
	if [[ "$2" -eq "-1" ]]; then
		return
	fi
	if [[ $DEBUG == "TRUE" ]]; then
		if [[ $# -eq 2 ]]; then
			for (( i = 0; i < $2; i++ )); do
				echo -n " "
			done
		fi
		echo -e "$1"
	fi
}
cfe(){
	
	[[ -f "$1" ]] && return 0 || return 1
}
#check if directory exist
cde(){
	[[ -d "$1" ]] && return 0 || return 1
}
#cde and create if not
cdeC(){
	cde "$1" $2 && printD "Directory exist $1" $2 || cdir "$1" $2
}
#cfe and create if not
cfeC(){
	 
	 
	if cfe "$1" $2;then
		printD "'File exist '$1" $2
		return 1
	else
		if [[ "$s" == "TRUE" ]]; then
			return 0
		fi
		cfile "$1" $2
		return 0
	fi
	# && ( a=$?;printD "File exist $1" $2; return 1; ) || ( cfile "$1" $2; return 0; )
}
#create directory
cdir(){
	printD "Create DIR $1" $2
	mkdir -p "$1"
}
#create file
cfile(){
	printD "CreateFile $1" $2
	touch "$1"
}
fletter(){
	echo "${1::1}"
}
lletter(){
	echo "${1:${#1}-1}"
}
ulletter(){
	echo "${1::${#1}-1}"
}
sfletter(){
	echo "${1:1:${#1}}"
}
toLower(){
	 echo $(tr '[:upper:]' '[:lower:]' <<<"$1")
}

cf_vide_empty(){
	# echo $line
	if [[ -f $1 ]]; then
		if [[ $(wc -l < $1) -gt 0 ]]; then
			return 1
		fi
	fi
	return 0
 	# [[  ( -f $1  &&  "$line" -gt 0 ) ]] && return 1 || return 0
}
fLower(){
	f=$(toLower `fletter $1` )
	echo "$f$(sfletter $1)"
}
change(){
	filen="$3"
	quoi="$1"
	avec="$2"
	sed -i -r "s|$quoi|$avec|g" "$filen" 
}
replace(){
	quoi="$1"
	avec="$2"
	str="$3"
	echo $(sed -r "s|$quoi|$avec|g" <<<"$str")
}
changeMoustache(){
	change "\{\{"$1"\}\}" $2 $3 
}

kMoustache(){
	ok="$1"
	kw="$2"
	fil="$3"
	t="$4"
	if [[ "$ok" == "TRUE" ]]; then
	if [[ $t == "F" ]]; then
		#statements

		sed -i -r "s/\{\{$kw\:(.*)\}\}/\1/" "$fil"
	else
sed -i -r "
/\{\{$kw\:/,/\}\}/{

	/\{\{$kw\:.*\}\}/ {
		s/\{\{$kw\:(.*)\}\}/\1/
	}

	/\{\{$kw\:.*\}\}/!{
	 /\{\{$kw\:/ { H;d } 
	 /\}\}/ { 
	 H
	 x
	 s/\{\{$kw\:(.*)\}\}/\1/
		D
		}
	/\{\{$kw\:/!{
		 /\}\}/! {
			H;d
		 }
	}
}
}" "$fil"
fi	
	else
		if [[ $t == "F" ]]; then
			sed -i -r "/(.*\{\{$kw\:.*\}\}.*)/d" "$fil"
		else
			sed -i -r "/\{\{$kw\:/,/\}\}/d" "$fil"
		fi
	fi
}

nokMoustache(){
	kw="$1"
	fil="$2"
	t="$3"
	kMoustache "FALSE" "$kw" "$fil" "$t"
}
okMoustache(){
	kw="$1"
	fil="$2"
	t="$3"
	kMoustache "TRUE" "$kw" "$fil" "$t"
}
inArr(){
	quoi="$1"
	arr=($2)
	for i in ${arr[@]}; do
		if [[ "$quoi" == "$i" ]]; then
			echo -n "0"
			exit
		fi
	done
	echo -n "1"
}
inArrI(){
	quoi="$1"
	f=$(splitStr $2)
	# echo "$2"
	arr=($f)
	# echo "${arr[@]}"
	# exit
	# arr=($f)
	inv="false"
	invp=""

	# echo "$arr"
	# if [[ ${#arr[@]} -eq 1 ]]; then
	# 	if [[ $( fletter $arr ) == "!" ]]; then
	# 		inv="true"
	# 		invp=$(sfletter $arr)
	# 	fi
	# fi
	for i in ${arr[@]}; do
		invp="$( fletter $i )"
		inv=$([[ $invp == "!" ]] && echo "true" || echo "false")
		# echo "$i"
		# echo "$inv"
		# echo "$quoi"
		# echo "elephant"
		# echo "$(inArr '!'"$quoi" "${arr[*]}" )"
		if [[ $(inArr '!'"$quoi" "${arr[*]}" ) == "1" && $inv == "true" ]]; then
			echo -n "0"
			exit
		elif [[ "$quoi" == "$i" && $inv == "false" ]]; then
			echo -n "0"
			exit
		fi
	done
	echo -n "1"
}
splitStr(){
	# old="$IFS"
	# IFS=","
	arr=()
	echo $(tr ',' '\n'<<<"$1")
	exit
	tr ',' '\n'<<<"$1" |while read i ;do
		arr+=($i)
	done
	echo "${arr[*]}"
	# IFD="$old"
}
