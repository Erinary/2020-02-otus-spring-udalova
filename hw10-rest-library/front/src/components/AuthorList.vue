<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h2>Authors
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
                        <th>Name</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="author in authors" :key="author.id">
                        <td>{{author.id}}</td>
                        <td>
                            <router-link :to="{name: 'author-details', params: {id: author.id}}">{{author.name}}
                            </router-link>
                        </td>
                        <td>
                            <div class="btn btn-outline-danger btn-sm" role="button"
                                 v-on:click="deleteAuthor(author.id)">Delete
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
        name: "AuthorList",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
                authors: [
                    {id: 1, name: "John Doe"}
                ]
            };
        },

        mounted() {
            this.loadAuthors();
        },

        methods: {
            loadAuthors() {
                this.loadingState = 'loading';
                client.getAuthorList()
                    .then(authorList => {
                        this.authors = authorList;
                        this.loadingState = 'ok';
                    })
                    .catch(e => {
                        this.error = e.toString();
                        this.loadingState = 'error';
                        this.showErrorAlert = true;
                    });
            },

            deleteAuthor(id) {
                client.deleteAuthor(id)
                    .then(() => this.loadAuthors())
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