import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import Vue from 'vue'
import VueRouter from "vue-router";
import App from './App.vue'
import HomePage from "./components/HomePage";
import BookList from "./components/BookList";
import BookDetails from "./components/BookDetails";
import BookSaveForm from "./components/BookSaveForm";
import AuthorList from "./components/AuthorList";
import AuthorDetails from "./components/AuthorDetails";
import GenreList from "./components/GenreList";
import GenreDetails from "./components/GenreDetails";

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

const routes = [
    {path: '/', name: 'home', component: HomePage},
    {path: '/book/all', name: 'books', component: BookList},
    {path: '/book/all/:id', name: 'book-details', component: BookDetails},
    {path: '/book/new', name: 'book-new', component: BookSaveForm},
    {path: '/book/all/:id/edit', name: 'book-edit', component: BookSaveForm},
    {path: '/author/all', name: 'authors', component: AuthorList},
    {path: '/author/:id', name: 'author-details', component: AuthorDetails},
    {path: '/genre/all', name: 'genres', component: GenreList},
    {path: '/genre/:id', name: 'genre-details', component: GenreDetails}
]

const router = new VueRouter({
    routes
})

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')
