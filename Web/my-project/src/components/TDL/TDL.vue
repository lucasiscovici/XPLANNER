<template>

<div>
	<section class="todoapp">
  <div class="todoappD">
  <h1 class="do">{{title}}</h1>
    <span style="
    text-align:  center;
    position: relative;
    width: 100%;
"><h3 style="    background-color: #222222;" class="tcenter c-whiteI">{{helper}}</h3></span>
</div>
  <header class="header">

    <input class="new-todo"
      autofocus autocomplete="off"
      placeholder="Qu'est ce qui doit être fait?"
      v-model="newTodo"
      @keyup.enter="addTodo">
  </header>
  <section class="main" v-show="todos.length" v-cloak>
    <input class="toggle-all" type="checkbox" v-model="allDone">
    <ul class="todo-list">

<draggable v-model="myArray" :move="checkMove">
      <li v-for="todo in myArray"
        class="todo"
        :key="todo.id"
        :class="{ completed: todo.completed, editing: todo == editedTodo }">
        <div class="view">
          <input class="toggle" type="checkbox" v-model="todo.completed" @click="edited">
          <label @dblclick="editTodo(todo)">{{ todo.title }}</label>
          <button class="destroy" @click="removeTodo(todo)"></button>
        </div>
        <input class="edit" type="text"
          v-model="todo.title"
          v-todo-focus="todo == editedTodo"
          @blur="doneEdit(todo)"
          @keyup.enter="doneEdit(todo)"
          @keyup.esc="cancelEdit(todo)">
          
      </li> 
      </draggable>
    </ul>
  </section>
  <footer class="footer" v-show="todos.length" v-cloak>
    <span class="todo-count">
      <strong>{{ remaining }}</strong> {{ remaining | pluralize }} left
    </span>
    <ul class="filters">
      <li><a @click="visibility='all'" :class="{ selected: visibility == 'all' }">All</a></li>
      <li><a @click="visibility='active'" :class="{ selected: visibility == 'active' }">Active</a></li>
      <li><a @click="visibility='completed'" :class="{ selected: visibility == 'completed' }">Completed</a></li>
      <!-- <li><a @click="co();move+=1" v-if="drop">{{m[move%2]}}</a></li> -->
      <!-- <li><a @click="drop=!drop" >{{!drop ? 'Move' : 'Static'}}</a></li> -->
    </ul>
    <button class="clear-completed" @click="removeCompleted" v-show="todos.length > remaining">
      Clear completed
    </button>
  </footer>
</section>
</div>
</template>
<script>
import TK  from "../utils/tk.js"
// handle routing
import draggable from 'vuedraggable'

var filters = {
  all: function (todos) {
    return todos
  },
  active: function (todos) {
    return todos.filter(function (todo) {
      return !todo.completed
    })
  },
  completed: function (todos) {
    return todos.filter(function (todo) {
      return todo.completed
    })
  }
}
function onHashChange () {
  var visibility = window.location.hash.replace(/#\/?/, '')
  if (filters[visibility]) {
    app.visibility = visibility
  } else {
    window.location.hash = ''
    app.visibility = 'all'
  }
}

window.addEventListener('hashchange', onHashChange);
onHashChange();
	export default {

	props:[
		"loader",
		"title",
    "customName",
    "helper"

	],
  // app initial state
  data(){ 
    return {
      todos: [],
      newTodo: '',
      editedTodo: null,
      visibility: 'all',
      okW:false,
      crud: this.com(),
      traduction:{name:"title",done:"completed"},
      m:["Up","Down"],
      move:0,
      drop:true,
      coco:true,
      noOb:false
    }
  },
  mounted(){

    console.log("MOUNTED TDL")
    this.$parent.$on("refreshTDL",()=>{

      console.log("refreshTDL")
      // this.customName2=this.$parent.customTDL2() ;
      this.crud=this.$fusion[this.$parent.customTDL2()]
      this.getTodos();

    })
    this.crud=this.com()
  	this.getTodos();
  },

  // watch todos change for localStorage persistence
  // watch: {
  //   todos: {
  //     handler: function (todos) {
  //     	// console.log()
  //     	if (this.okW){
  //     		// this.okW=false
  //     	var self=this;
  //       this.crud.post(TK.noKeys(TK.changeKeyName(this.todos,this.traduction,true,true),["id"],true)).then((res)=>{
  //       	this.okW=false
  //       	self.changeFake(TK.changeKeyName(res.data,this.traduction,true,true));

  //       	this.okW=true

  //       });
  //   }
  //     },
  //     deep: true
  //   }
  // },

  // computed properties
  // http://vuejs.org/guide/computed.html
  computed: {

    customName2:{
      get(){
      return this.customName;
      },
      set(k){

      }
    },
   
    filteredTodos: function () {
     var d = filters[this.visibility](this.todos)
     // this.okW=true
     return d
    },
    remaining: function () {
      return filters.active(this.todos).length
    },
    allDone: {
      get: function () {
        return this.remaining === 0
      },
      set: function (value) {
        this.todos.forEach(function (todo) {
          todo.completed = value
        })
      }
    },
    myArray: {
        get() {
            return this.filteredTodos
        },
        set(value) {
        	console.log("SET")
        	console.log(value)
        	TK.unObs(value)
        	console.log(value)
        	if (this.visibility=="all") {
		        	this.todos = value
		        }else if(this.visibility=="active"){
		        	this.todos = TK.arrConcat(value,TK.unObs(this.filteredTodosM("completed",this.todos),true),this.coco)
		        }else if(this.visibility=="completed"){

		        	this.todos =  TK.arrConcat(TK.unObs(this.filteredTodosM("active",this.todos),true) ,value,this.coco)
		        }
        	// this.todos = value
            // this.$store.commit('updateList', value)
        }
    }
  },

  filters: {
    pluralize: function (n) {
      return n === 1 ? 'item' : 'items'
    }
  },
  components:{draggable},

  // methods that implement data logic.
  // note there's no DOM manipulation here at all.
  methods: {
     com(){
      console.log("ici");
      console.log(this.customName2);
      return this.$fusion[this.customName2]
    },
    edited(){
      setTimeout(this.change,500)
// this.change()
    },
    change(){
        var self=this;
        this.crud.post(TK.noKeys(TK.changeKeyName(this.todos,this.traduction,true,true),["id"],true)).then((res)=>{
          this.okW=false
          self.changeFake(TK.changeKeyName(res.data,this.traduction,true,true));

          this.okW=true

        });
    },
  	checkMove(evt){
  		return this.drop
  	},
  	co(){
  		this.todos = TK.arrConcat(TK.unObs(this.filteredTodosM("active",this.todos),true),TK.unObs(this.filteredTodosM("completed",this.todos),true),this.coco)
      this.change()
  		this.coco=!this.coco
  		return this.coco
  	},
  	filteredTodosM(q,t) {
    	q = q==undefined ? this.visibility : q
      return filters[q](t)
    },
  	changeFake(aa){
  		// console.log(this.todos)
  		this.okW=false
  		this.todos.forEach(d=>{d["id"] = aa.filter(df=>df.fId==d.fId)[0].id});
        this.okW=true
  		// setTimeout(()=>{this.okW=true},500);
  		// this.todos.sort((a,b)=>a.completed==b.completed && a.completed)
  		// this.todos=TK.arrConcat(TK.unObs(this.filteredTodosM("active",this.todos),true),TK.unObs(this.filteredTodosM("completed",this.todos),true),this.co)
  	},
  	tranformToTodos(d){
  		return TK.changeKeyName(d,this.traduction,false,true)
  	},
  	getTodos(){
  		console.log(this.crud);
  		console.log(this.customName);
  		// this.$fusion[this.customName].get()
  		this.crud.get().then((res)=>{
        console.log(res);
        this.todos=res==undefined ? [] : this.tranformToTodos(res.data);
  			// if (this.drop) {
  			// 	this.todos = k.sort((a,b)=>a.completed==b.completed && a.completed)
  			// }
// this.change()
  			setTimeout(()=>this.okW=true,1000);
  			if (this.todos.length>0){
  				// console.log(this.todos[0])
  				if(this.todos[0].completed==true){
  					this.move+=1
  				}
  			}
  			console.log(this.todos)
  		});
  	},
    addTodo: function () {
    	var self = this;
      var value = this.newTodo && this.newTodo.trim()
      if (!value) {
        return
      }
      var s = {
      	// id:null,
        fId: "FAKE_"+TK.randomString(),
        title: value,
        completed: false
      };
     self.todos.push(s)
     this.change()
      // this.crud.push(TK.changeKeyName(s,this.traduction,true)).then((res)=> self.todos.push(res.data));
      this.newTodo = ''
    },

    removeTodo: function (todo) {
      this.todos.splice(this.todos.indexOf(todo), 1)
      this.change()
       // this.crud.delete(todo.id,true);
    },

    editTodo: function (todo) {
      this.beforeEditCache = todo.title
      this.editedTodo = todo
      
    },

    doneEdit: function (todo) {
      if (!this.editedTodo) {
        return
      }
      this.editedTodo = null
      todo.title = todo.title.trim()
      if (!todo.title) {
        this.removeTodo(todo)
      }
      this.change()
    },

    cancelEdit: function (todo) {
      this.editedTodo = null
      todo.title = this.beforeEditCache
    },

    removeCompleted: function () {
      this.todos = filters.active(this.todos)
      this.change()
    }
  },

  // a custom directive to wait for the DOM to be updated
  // before focusing on the input field.
  // http://vuejs.org/guide/custom-directive.html
  directives: {
    'todo-focus': function (el, binding) {
      if (binding.value) {
        el.focus()
      }
    }
  }




// // mount
// app.$mount('.todoapp')
	}

		// Full spec-compliant TodoMVC with localStorage persistence
// and hash-based routing in ~120 effective lines of JavaScript.

// localStorage persistence
// var STORAGE_KEY = 'todos-vuejs-2.0'
// var todoStorage = {
//   fetch: function () {
//     var todos = JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]')
//     todos.forEach(function (todo, index) {
//       todo.id = index
//     })
//     todoStorage.uid = todos.length
//     return todos
//   },
//   save: function (todos) {
//     localStorage.setItem(STORAGE_KEY, JSON.stringify(todos))
//   }
// }

// visibility filters


// app Vue instance




</script>

<style scoped lang="scss">
	html,
body {
	margin: 0;
	padding: 0;
}

button {
	margin: 0;
	padding: 0;
	border: 0;
	background: none;
	font-size: 100%;
	vertical-align: baseline;
	font-family: inherit;
	font-weight: inherit;
	color: inherit;
	-webkit-appearance: none;
	appearance: none;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
}

body {
	font: 14px 'Helvetica Neue', Helvetica, Arial, sans-serif;
	line-height: 1.4em;
	background: #f5f5f5;
	color: #4d4d4d;
	min-width: 230px;
	max-width: 550px;
	margin: 0 auto;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	font-weight: 300;
}

:focus {
	outline: 0;
}

.hidden {
	display: none;
}

.todoapp {
  // overflow: scroll!important;
    // height: 59vh;
	background: #fff;
	// margin: 130px 0 40px 0;
	position: relative;
	box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.2),
	            0 25px 50px 0 rgba(0, 0, 0, 0.1);
}

.todoapp input{

&::-webkit-input-placeholder {
	font-style: italic;
	font-weight: 300;
	color: $brown;
 }
 &::-moz-placeholder {
	font-style: italic;
	font-weight: 300;
	color: $brown;
}
&::input-placeholder {
	font-style: italic;
	font-weight: 300;
	color: $brown;
}
}
.todoappD{
  background-color: #222222!important;
}
.todoapp h1 {
	position: relative;
	width: 100%;
	/*font-size: 100px;*/
	font-weight: 100;
	text-align: center;
	color: white;
	-webkit-text-rendering: optimizeLegibility;
	-moz-text-rendering: optimizeLegibility;
	text-rendering: optimizeLegibility;
}

.new-todo,
.edit {
	position: relative;
	margin: 0;
	width: 100%;
	font-size: 24px;
	font-family: inherit;
	font-weight: inherit;
	line-height: 1.4em;
	border: 0;
	color: inherit;
	padding: 6px;
	border: 1px solid #999;
	box-shadow: inset 0 -1px 5px 0 rgba(0, 0, 0, 0.2);
	box-sizing: border-box;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
}

.new-todo {
	padding: 16px 16px 16px 60px;
	border: none;
	background: rgba(0, 0, 0, 0.003);
	box-shadow: inset 0 -2px 1px rgba(0,0,0,0.03);
}

.main {
	position: relative;
	z-index: 2;
	border-top: 1px solid #e6e6e6;
}

label[for='toggle-all'] {
	display: none;
}

.toggle-all {
	position: absolute;
	top: -55px;
	left: -12px;
	width: 60px;
	height: 34px;
	text-align: center;
	border: none; /* Mobile Safari */
}

.toggle-all:before {
	content: '❯';
	font-size: 22px;
	color: #e6e6e6;
	padding: 10px 27px 10px 27px;
}

.toggle-all:checked:before {
	color: #737373;
}

.todo-list {
	margin: 0;
	padding: 0;
	list-style: none;
}

.todo-list li {
	position: relative;
	font-size: 24px;
	border-bottom: 1px solid #ededed;
}

.todo-list li:last-child {
	border-bottom: none;
}

.todo-list li.editing {
	border-bottom: none;
	padding: 0;
}

.todo-list li.editing .edit {
	display: block;
	width: 506px;
	padding: 12px 16px;
	margin: 0 0 0 43px;
}

.todo-list li.editing .view {
	display: none;
}

.todo-list li .toggle {
	text-align: center;
	width: 40px;
	/* auto, since non-WebKit browsers doesn't support input styling */
	height: auto;
	position: absolute;
	top: 0;
	bottom: 0;
	margin: auto 0;
	border: none; /* Mobile Safari */
	-webkit-appearance: none;
	appearance: none;
}

.todo-list li .toggle:after {
	content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#ededed" stroke-width="3"/></svg>');
}

.todo-list li .toggle:checked:after {
	content: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="-10 -18 100 135"><circle cx="50" cy="50" r="50" fill="none" stroke="#bddad5" stroke-width="3"/><path fill="#5dc2af" d="M72 25L42 71 27 56l-4 4 20 20 34-52z"/></svg>');
}

.todo-list li label {
	word-break: break-all;
	padding: 15px 60px 15px 15px;
	margin-left: 45px;
	display: block;
	line-height: 1.2;
	transition: color 0.4s;
}

.todo-list li.completed label {
	color: #d9d9d9;
	text-decoration: line-through;
}

.todo-list li .destroy {
	display: none;
	position: absolute;
	top: 0;
	right: 10px;
	bottom: 0;
	width: 40px;
	height: 40px;
	margin: auto 0;
	font-size: 30px;
	color: #cc9a9a;
	margin-bottom: 11px;
	transition: color 0.2s ease-out;
}

.todo-list li .destroy:hover {
	color: #af5b5e;
}

.todo-list li .destroy:after {
	content: '×';
}

.todo-list li:hover .destroy {
	display: block;
}

.todo-list li .edit {
	display: none;
}

.todo-list li.editing:last-child {
	margin-bottom: -1px;
}

.footer {
	color: #777;
	padding: 10px 15px;
	height: 20px;
	text-align: center;
	border-top: 1px solid #e6e6e6;
}

.footer:before {
	content: '';
	position: absolute;
	right: 0;
	bottom: 0;
	left: 0;
	height: 50px;
	overflow: hidden;
	box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2),
	            0 8px 0 -3px #f6f6f6,
	            0 9px 1px -3px rgba(0, 0, 0, 0.2),
	            0 16px 0 -6px #f6f6f6,
	            0 17px 2px -6px rgba(0, 0, 0, 0.2);
}

.todo-count {
	float: left;
	text-align: left;
}

.todo-count strong {
	font-weight: 300;
}

.filters {
	margin: 0;
	padding: 0;
	list-style: none;
	position: absolute;
	right: 0;
	left: 0;
}

.filters li {
	display: inline;
}

.filters li a {
	color: inherit;
	margin: 3px;
	padding: 3px 7px;
	text-decoration: none;
	border: 1px solid transparent;
	border-radius: 3px;
}

.filters li a:hover {
	border-color: rgba(175, 47, 47, 0.1);
}

.filters li a.selected {
	border-color: $brown;
}

.clear-completed,
html .clear-completed:active {
	float: right;
	position: relative;
	line-height: 20px;
	text-decoration: none;
	cursor: pointer;
}

.clear-completed:hover {
	text-decoration: underline;
}

.info {
	margin: 65px auto 0;
	color: #bfbfbf;
	font-size: 10px;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	text-align: center;
}

.info p {
	line-height: 1;
}

.info a {
	color: inherit;
	text-decoration: none;
	font-weight: 400;
}

.info a:hover {
	text-decoration: underline;
}
[v-cloak] { display: none; }

/*
	Hack to remove background from Mobile Safari.
	Can't use it globally since it destroys checkboxes in Firefox
*/
@media screen and (-webkit-min-device-pixel-ratio:0) {
	.toggle-all,
	.todo-list li .toggle {
		background: none;
	}

	.todo-list li .toggle {
		height: 40px;
	}

	.toggle-all {
		-webkit-transform: rotate(90deg);
		transform: rotate(90deg);
		-webkit-appearance: none;
		appearance: none;
	}
}

@media (max-width: 430px) {
	.footer {
		height: 50px;
	}

	.filters {
		bottom: 10px;
	}
}

</style>