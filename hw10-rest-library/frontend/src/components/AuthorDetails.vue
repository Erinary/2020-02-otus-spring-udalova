<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h2>Author Details
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
                <router-link :to="{name: 'authors'}" class="btn btn-info btn-sm" role="button">← To author list
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
                        <td>{{author.id}}</td>
                        <td>{{author.name}}</td>
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
                    <tr v-for="book in author.books" :key="book.id">
                        <td>{{book.id}}</td>
                        <td>
                            <router-link :to="{name: 'book-details', params: {id: book.id}}"> {{book.title}}
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
        name: "AuthorDetails",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
                author:
                    {
                        id: 1,
                        name: "John Doe",
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
            this.loadAuthor(this.$route.params.id);
        },

        methods: {
            loadAuthor(id) {
                this.loadingState = 'loading';
                client.getAuthor(id)
                    .then(author => {
                        this.author = author;
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