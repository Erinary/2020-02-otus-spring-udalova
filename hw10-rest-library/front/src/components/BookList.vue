<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h2>Books
                    <router-link :to="{name: 'book-new'}" class="btn btn-outline-primary btn-sm" role="button">
                        Save new
                    </router-link>
                </h2>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col">
                <router-link :to="{name: 'home'}" class="btn btn-info btn-sm" role="button">← Home</router-link>
            </div>
        </div>

        <div class="row">
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
        </div>
    </div>
</template>

<script>
    import {client} from '../controller/api.js';

    export default {
        name: "BookList",

        data() {
            return {
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
                client.getBookList()
                    .then(bookList => {
                        this.books = bookList;
                    });
            },

            deleteBook(id) {
                client.deleteBook(id)
                    .then(() => this.loadBooks());
            }
        }
    }
</script>

<style scoped>

</style>