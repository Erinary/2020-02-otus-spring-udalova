<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h2>Books
                    <router-link :to="{name: 'book-new'}" class="btn btn-outline-primary btn-sm" role="button">
                        Save new
                    </router-link>
                    <b-spinner v-if="loadingState === 'loading'" class="ml-2"/>
                </h2>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-alert v-model="showErrorAlert" dismissible variant="danger">{{error}}</b-alert>
            </b-col>
        </b-row>
        <b-row class="mb-1">
            <b-col>
                <router-link :to="{name: 'home'}" class="btn btn-info btn-sm" role="button">← Home</router-link>
            </b-col>
        </b-row>

        <b-row v-if="loadingState === 'ok'">
            <div class="col">
                <table class="table">
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Year</th>
                        <th>Author</th>
                        <th>Genre</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="book in books" :key="book.id">
                        <td>{{book.id}}</td>
                        <td>
                            <router-link :to="{name: 'book-details', params: {id: book.id}}" href="book-details.html">
                                {{book.title}}
                            </router-link>
                        </td>
                        <td>{{book.year}}</td>
                        <td>{{book.author}}</td>
                        <td>{{book.genre}}</td>
                        <td>
                            <div class="btn btn-outline-danger btn-sm" role="button" v-on:click="deleteBook(book.id)">
                                Delete
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </b-row>
    </b-container>
</template>

<script>
    import {client} from '../controller/api.js';

    export default {
        name: "BookList",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
                books: [
                    {
                        id: 1,
                        title: "Book Title",
                        year: 1970,
                        author: "John Doe",
                        genre: "Crime fiction"
                    }
                ]
            }
        },

        mounted() {
            this.loadBooks();
        },

        methods: {
            loadBooks() {
                this.loadingState = 'loading';
                client.getBookList()
                    .then(bookList => {
                        this.books = bookList;
                        this.loadingState = 'ok';
                    })
                    .catch(e => {
                        this.error = e.toString();
                        this.loadingState = 'error';
                        this.showErrorAlert = true;
                    });
            },

            deleteBook(id) {
                client.deleteBook(id)
                    .then(() => this.loadBooks())
                    .catch(e => {
                        this.error = e.toString();
                        this.showErrorAlert = true;
                    });
            }
        }
    }
</script>

<style scoped>

</style>