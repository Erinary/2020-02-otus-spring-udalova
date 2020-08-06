<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h2>Genres</h2>
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
        </div>
    </div>
</template>

<script>
    import {client} from '../controller/api.js';

    export default {
        name: "GenreList",

        data() {
            return {
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
                client.getGenreList()
                    .then(genreList => {
                        this.genres = genreList
                    });
            },

            deleteGenre(id) {
                client.deleteGenre(id)
                    .then(() => this.loadGenres());
            }
        }
    }
</script>

<style scoped>

</style>