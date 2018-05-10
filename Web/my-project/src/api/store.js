import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)

// root state object.
// each Vuex instance is just a single state tree.
const state = {
  user: null,
  todos:null,
  pbResseau:false,
  sesscur:null,
  okDP:false,
  logged:false
}

// mutations are operations that actually mutates the state.
// each mutation handler gets the entire state tree as the
// first argument, followed by additional payload arguments.
// mutations must be synchronous and can be recorded by plugins
// for debugging purposes.
const mutations = {
  increment (state,us) {
    state.user=us
  },
  incrementt (state,us) {
    state.todos=us
  },
  rmT(state){
    state.todos = null
  },
  rmU(state){
    state.user = null
  },
  rmSU(state){
    state.sesscur=null
  },
  sesscur(state,s){
    state.sesscur=s
  },
  rmOKDP(state){
    state.okDP=false
    state.logged=false
  },
  rmUISessCurr(state){
    // state.user.userInfo=null
  }

}

// actions are functions that cause side effects and can involve
// asynchronous operations.
const actions = {
  postUser: ({ commit },us) => commit('increment',us),
  setTodos: ({ commit },us) => commit('incrementt',us),
  rmTodos: ({ commit }) => commit('rmT'),
  rmUser: ({ commit }) => commit('rmU'),
  rmSessCurr:({commit}) => {commit('rmSU');commit("rmUISessCurr");},
  rmInfos:({commit}) => {commit('rmT');commit('rmU');commit("rmSU");commit("rmOKDP")},
  setSessCurr:({commit},s) => commit("sesscur",s),
  rmUISessCurr:({commit}) =>commit("rmUISessCurr"),
  rmOKDP:({commit}) =>commit("rmOKDP")
  }


// getters are functions
const getters = {
  user: state => state.user,
  todos: state => state.todos,
  sesscur: state => state.sesscur
}
// const myPlugin = store => {
//   socket.on('data', data => {
//       store.commit('receiveData', data)
//     }),
//   // called when the store is initialized
//   store.subscribe((mutation, state) => {
//     // called after every mutation.
//     localStorage.setItem('store', JSON.stringify(state));
//     // The mutation comes in the format of `{ type, payload }`.
//   })
// }
// A Vuex instance is created by combining the state, mutations, actions,
// and getters.
export default new Vuex.Store({
  state,
  getters,
  actions,
  mutations,
  plugins: [createPersistedState()]
})
// Vue.$store=Vue.$store.cache
