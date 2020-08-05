import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Vue from 'vue'
import VueRouter from "vue-router";
import App from './App.vue'
import HomePage from "./components/HomePage";
import AuthorList from "./components/AuthorList";
import AuthorDetails from "./components/AuthorDetails";

Vue.config.productionTip = false
Vue.use(VueRouter)

const routes = [
    {path: '/', name: 'home', component: HomePage},
    {path: '/authors', name: 'authors', component: AuthorList},
    {path: '/author/:id', name: 'author-details', component: AuthorDetails}
]

const router = new VueRouter({
    routes
})

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')
