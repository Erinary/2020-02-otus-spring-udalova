import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
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

const routes = [
    {path: '/', name: 'home', component: HomePage},
    {path: '/books', name: 'books', component: BookList},
    {path: '/book/all/:id', name: 'book-details', component: BookDetails},
    {path: '/book/new', name: 'book-new', component: BookSaveForm},
    {path: '/book/all/:id/edit', name: 'book-edit', component: BookSaveForm},
    {path: '/authors', name: 'authors', component: AuthorList},
    {path: '/author/:id', name: 'author-details', component: AuthorDetails},
    {path: '/genres', name: 'genres', component: GenreList},
    {path: '/genre/:id', name: 'genre-details', component: GenreDetails}
]

const router = new VueRouter({
    routes
})

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')
