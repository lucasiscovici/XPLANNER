<template>
  <div id="app" style="overflow-y: hidden;max-height: 100vh;background-color: black;"  v-if="$auth.ready()">
  <transition name="fade">
    <router-view ></router-view>   
    </transition>
  </div>
</template>

<script>

export default {
  props:["params"],
  name: 'app',
  data () {
    return {
      // msg: 'Planning 90 | Feedback',
      // cacaol: this.cocaol
    }
  }
}
</script>

<style lang="scss">
 // @import 'var.scss';
.tcenter{
  text-align: center;
}
*{

    box-shadow:none!important;
outline: none!important;  
    box-sizing: inherit;
}
   a  {
    // color : white !important;
  }

  .cursorPointer {
    cursor:   pointer;
  }

  .loading {    
    background-color: #ffffff;
    background-image: url("/src/assets/3.gif");
    background-size: 25px 25px;
    background-position:right center;
    background-repeat: no-repeat;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  // -moz-osx-font-smoothing: grayscale;
  // text-align: center;
  // color: #2c3e50;
  // margin-top: 60px;
}

h1, h2 {
  // font-weight: normal;
}

ul {
  // list-style-type: none;
  // padding: 0;
}

// li {
//   display: inline-block;
//   margin: 0 10px;
// }

// a {
//   color: #42b983;
// }

    input:-webkit-autofill,
textarea:-webkit-autofill,
select:-webkit-autofill
{
    &, &:hover, &:focus, &:active
    {
        -webkit-box-shadow: 0 0 0px 1000px white inset !important;
    }
}
</style>
