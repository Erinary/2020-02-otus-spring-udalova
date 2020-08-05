<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h2>Authors</h2>
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
        </div>
    </div>
</template>

<script>
    import {client} from '../controller/api.js'

    export default {
        name: "AuthorList",

        data() {
            return {
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
                client.getAuthorList()
                    .then(authorList => {
                        this.authors = authorList;
                    });
            },

            deleteAuthor(id) {
                client.deleteAuthor(id)
                    .then(() => this.loadAuthors());
            }
        }
    }
</script>

<style scoped>

</style>