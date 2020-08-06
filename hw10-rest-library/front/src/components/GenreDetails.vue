<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h2>Genre Details</h2>
            </div>
        </div>
        <div class="row mb-1">
            <div class="col">
                <router-link :to="{name: 'genres'}" class="btn btn-info btn-sm" role="button">← Back</router-link>
            </div>
        </div>

        <div class="row">
            <div class="col-8">
                <table class="table table-sm">
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>{{genre.id}}</td>
                        <td>{{genre.name}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col-8">
                <table class="table">
                    <thead class="thead-light">
                    <tr>
                        <th>Book ID</th>
                        <th>Title</th>
                        <th>Year</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="book in genre.books" :key="book.id">
                        <td>{{book.id}}</td>
                        <td>
                            <router-link :to="{name: 'book-details', params: {id: book.id}}">{{book.title}}</router-link>
                        </td>
                        <td>{{book.year}}</td>
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
        name: "GenreDetails",

        data() {
            return {
                genre: {
                    id: 1,
                    name: "Crime fiction",
                    books: [
                        {
                            id: 1,
                            title: "Book Title",
                            year: 1970
                        }
                    ]
                }
            }
        },

        mounted() {
            this.loadGenre(this.$route.params.id);
        },

        methods: {
            loadGenre(id) {
                client.getGenre(id)
                    .then(genre => {
                        this.genre = genre;
                    });
            }
        }
    }
</script>

<style scoped>

</style>