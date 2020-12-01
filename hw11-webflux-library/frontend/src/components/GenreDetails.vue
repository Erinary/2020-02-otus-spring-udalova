<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h2>Genre Details
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
                <router-link :to="{name: 'genres'}" class="btn btn-info btn-sm" role="button">← To genre list
                </router-link>
            </b-col>
        </b-row>

        <b-row class="mb-2" v-if="loadingState === 'ok'">
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
        </b-row>

        <b-row v-if="loadingState === 'ok'">
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
                            <router-link :to="{name: 'book-details', params: {id: book.id}}">{{book.title}}
                            </router-link>
                        </td>
                        <td>{{book.year}}</td>
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
        name: "GenreDetails",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
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
                this.loadingState = 'loading';
                client.getGenre(id)
                    .then(genre => {
                        this.genre = genre;
                        this.loadingState = 'ok';
                    })
                    .catch(e => {
                        this.error = e.toString();
                        this.loadingState = 'error';
                        this.showErrorAlert = true;
                    });
            }
        }
    }
</script>

<style scoped>

</style>