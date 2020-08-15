<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h2>Genres
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
                    <tr v-for="genre in genres" :key="genre.id">
                        <td>{{genre.id}}</td>
                        <td>
                            <router-link :to="{name: 'genre-details', params: {id: genre.id}}">{{genre.name}}
                            </router-link>
                        </td>
                        <td>
                            <div class="btn btn-outline-danger btn-sm" role="button"
                                 v-on:click="deleteGenre(genre.id)">Delete
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
        name: "GenreList",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
                genres: [
                    {id: 1, name: "Crime fiction"}
                ]
            };
        },

        mounted() {
            this.loadGenres();
        },

        methods: {
            loadGenres() {
                this.loadingState = 'loading';
                client.getGenreList()
                    .then(genreList => {
                        this.genres = genreList;
                        this.loadingState = 'ok';
                    })
                    .catch(e => {
                        this.error = e.toString();
                        this.loadingState = 'error';
                        this.showErrorAlert = true;
                    });
            },

            deleteGenre(id) {
                client.deleteGenre(id)
                    .then(() => this.loadGenres())
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