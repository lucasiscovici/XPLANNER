# GENERATE Controller + Service / ServImpl + Repository
DIRO="$(dirname $0)/"
source $DIRO"color.sh"
source $DIRO"functools.sh"
source $DIRO"params.sh"

usage(){

  echo "USAGE: [CONF] NOM_TO_GENERATE"
  echo  -e "[CONF]: --conf-base -cb
--conf-ctrl -cc
--conf-serv -cv
--conf-serv-impl -csi
--conf-rep -cr
--package -pk
--model -m
--extends-model -em 
--debug -d
--conf-ctrl-ext -cce
--conf-serv-ext -cse
--conf-serv-impl-ext -csie
--conf-rep-ext -cre
--conf-model-ext -cme
--force -f
--force-config -fc (xxx,yyy,zzz) (!xxx) (Pkg Model Serv ServImpl Rep Ctrl)
--add-rep-in-ctrl -repCtrl 
--supp -s name
[CONF=*]"
}

if [[ $# -eq 0 ]]; then
  usage
  exit
fi

addRep="FALSE"
###########OPTS###################
for i in "$@"
do
case $i in
    --conf-base=*|-cb=*)
    configuration_base="${i#*=}"
    shift # past argument=value
    ;;
    --conf-ctrl=*|-cc=*)
    ctrl="${i#*=}"
    shift # past argument=value
    ;;
     --conf-serv=*|-cs=*)
    serv="${i#*=}"
    shift # past argument=value
    ;;
     --conf-serv-impl=*|-csi=*)
    impl="${i#*=}"
    shift # past argument=value
    ;;
     --conf-rep=*|-cr=*)
    rep="${i#*=}"
    shift # past argument=value
    ;;
     --conf-package=*|-cpkg=*)
    pkg="${i#*=}"
    shift # past argument=value
    ;;
     --conf-model=*|-cm=*)
    model="${i#*=}"
    shift # past argument=value
    ;;
    --extends-model=*|-em=*)
    emodel="${i#*=}"
    shift # past argument=value
    ;;
    --debug|-d)
    DEBUG="TRUE"
    shift # past argument with no value
    ;;
    --conf-ctrl-ext=*|-cce=*)
    ctrlExt="${i#*=}"
    shift # past argument=value
    ;;
     --conf-serv-ext=*|-cse=*)
    servExt="${i#*=}"
    shift # past argument=value
    ;;
     --conf-serv-impl-ext=*|-csie=*)
    implExt="${i#*=}"
    shift # past argument=value
    ;;
     --conf-rep-ext=*|-cre=*)
    repExt="${i#*=}"
    shift # past argument=value
    ;;
    --conf-model-ext=*|-cme=*)
    modelExt="${i#*=}"
    shift # past argument=value
    ;;
    --force|-f)
    FORCE="TRUE"
    shift # past argument=value
    ;;
    --force-config=*|-fc=*)
    forceConfig="${i#*=}"
    shift # past argument=value
    ;;
    --add-rep-in-ctrl|-repCtrl)
    addRep="TRUE"
    shift # past argument=value
    ;;
    --help|-h)
    HELP="TRUE"
    shift # past argument=value
    ;;
    --supp|-s)
    supp="TRUE"
    shift # past argument=value
    ;;
    -*=*|--*=*)
  echo "unknown option ${i%=*}"
  shift
  ;;
    *)
          # unknown option
    ;;
esac
done

if [[ $HELP == "TRUE" ]]; then
  usage 
  exit
fi
#IMPL CHECK
if [[ $(fletter $impl) == "*" ]]; then
  impl=$serv"/"$(sfletter $impl)
fi

#PKG CHECK
if [[ -z "$pkg" ]]; then
  pw=$(cd "$configuration_base"; pwd)
  pkg="${pw#*src/main/java/}"
  if [[ ($(fletter $pkg) == ".")  ||  ($(fletter $pkg) == "/") ]]; then
    echo "Error PKG $pkg"
    exit
  fi
fi

#conf_base CHECK
if [[ "$(lletter $configuration_base)" != "/" ]]; then
  configuration_base="$configuration_base""/"
fi

configuration_ctrl="$configuration_base$ctrl"
configuration_serv="$configuration_base$serv"
configuration_serv_impl="$configuration_base$impl"
configuration_rep="$configuration_base$rep"
configuration_pkg="$pkg"
configuration_model="$configuration_base$model"
configuration_emodel="$emodel"
configuration_force="$forceConfig"
printD "$(color_brown 'Configurations:')"
printD "Base       = ${configuration_base}" 1
printD "Controller = ${configuration_ctrl}" 1
printD "Serv       = ${configuration_serv}" 1
printD "Serv_impl  = ${configuration_serv_impl}" 1      
printD "Rep        = ${configuration_rep}" 1
printD "PKG        = ${configuration_pkg}" 1
printD "Model      = ${configuration_model}" 1
printD "EModel     = ${configuration_emodel}" 1

#CHECK IF PB NAME
if [[ -z $1 ]]; then
    printD "$(color_red 'PB MANQUE NOM_TO_GENERATE')"
    # if [[ $DEBUG == "FALSE" ]];then
      exit
    # fi
fi

names="$@"


#Create Dir
printD "`color_brown 'Create Dir:'`"
DIR=( "$configuration_ctrl" "$configuration_serv" "$configuration_serv_impl" "$configuration_rep" "$configuration_model" )

bail=("Pkg" "Model" "Serv" "ServImpl" "Rep" "Ctrl")
confi=("$pkg" "$model" "$serv" "$impl" "$rep" "$ctrl")
conf=("$configuration_pkg" "$configuration_model" "$configuration_serv" "$configuration_serv_impl" "$configuration_rep" "$configuration_ctrl")
ext=("LUCUXNO"  "$modelExt" "$servExt" "$implExt" "$repExt" "$ctrlExt")

source $DIRO"funcGenerator.sh"
for i in ${DIR[@]}; do
  cdeC "$i" 1
done

fConf(){
  name="$1"
for (( ii = 0; ii < ${#conf[@]}; ii++ )); do
    if [[ "${ext[$ii]}" != "LUCUXNO"  ]]; then
        aCreer="${conf[$ii]}/$name${ext[$ii]}"".java"
        printD "${bail[$ii]} $ii) $aCreer" 2
        cfeC "$aCreer.tmp" -1 "$supp"
        cf_vide_empty "$aCreer" -1
        a=$?
        # echo $a
        # printD "$(color_red "'$FORCE ${bail[$ii]} $configuration_force'")"
        echo "$(toLower ${bail[ii]})"
        # exit
        if [[ ( ($FORCE == "TRUE" ) || $(inArrI ${bail[$ii]} "$configuration_force") == "0"  ) ]];then
          xxxT "$(toLower ${bail[ii]})" "$aCreer" "$name" 1 "$supp"
          printD "$(color_yellow "${bail[ii]}) $ii  Effectué")" 2
        else
          printD "$(color_yellow "${bail[ii]}) $ii  Passé")" 2
        fi
        

        # continue
    fi
  done
}
for name in $names; do
  printD "`color_brown 'Create Files for '$name':'`"
  fConf "$name"
done


###########################
#####
###########################


