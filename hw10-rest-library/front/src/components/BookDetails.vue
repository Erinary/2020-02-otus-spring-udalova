<template>
    <b-container fluid>
        <b-row>
            <div class="col">
                <h2>Book details
                    <router-link :to="{name: 'book-edit', params: {id: book.id}}"
                                 class="btn btn-outline-primary btn-sm" role="button">Edit
                    </router-link>
                    <b-spinner v-if="loadingState === 'loading'" class="ml-2"/>
                </h2>
            </div>
        </b-row>
        <b-row>
            <b-col>
                <b-alert v-model="showErrorAlert" dismissible variant="danger">{{error}}</b-alert>
            </b-col>
        </b-row>
        <b-row class="mb-2">
            <b-col>
                <router-link :to="{name: 'books'}" class="btn btn-info btn-sm" role="button">
                    ← To book list
                </router-link>
            </b-col>
        </b-row>

        <b-row class="mb-2" v-if="loadingState === 'ok'">
            <div class="col">
                <table class="table table-sm">
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Year</th>
                        <th>Author</th>
                        <th>Genre</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>{{book.id}}</td>
                        <td>{{book.title}}</td>
                        <td>{{book.year}}</td>
                        <td>{{book.author}}</td>
                        <td>{{book.genre}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </b-row>

        <b-row v-if="loadingState === 'ok'">
            <div class="col">
                <h4>Comments</h4>
                <b-alert show variant="info" v-if="book.comments.length === 0">There are no comments
                    yet
                </b-alert>
                <table class="table" v-else>
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Text</th>
                        <th>User</th>
                        <th>Date</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="comment in book.comments" :key="comment.id">
                        <td>{{comment.id}}</td>
                        <td>{{comment.text}}</td>
                        <td>{{comment.user}}</td>
                        <td>{{comment.date.format('YYYY-MM-DD HH:mm:ss')}}</td>
                        <td>
                            <div class="btn btn-outline-danger btn-sm" role="button"
                                 v-on:click="deleteComment(comment.id)">
                                Delete
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </b-row>
        <b-row v-if="loadingState === 'ok'">
            <b-col class="col-5">
                <b-form>
                    <b-form-group>
                        <b-form-textarea v-model="newComment.text"
                                         placeholder="Your text here"></b-form-textarea>
                    </b-form-group>
                </b-form>
                <b-form inline @submit="saveComment()">
                    <b-form-group>
                        <b-form-input v-model="newComment.user"></b-form-input>
                    </b-form-group>
                    <b-button class="btn btn-info ml-2" type="submit">Add new</b-button>
                </b-form>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    import {client} from '../controller/api.js';
    import moment from 'moment';

    export default {
        name: "BookDetails",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
                book: {
                    id: 1,
                    title: "Book Title",
                    year: 1970,
                    author: "John Doe",
                    genre: "Crime fiction",
                    comments: [
                        {
                            id: 1,
                            text: "Comment text here",
                            user: "Guest",
                            date: moment("1970-01-01 00:00:00")
                        }
                    ]
                },
                newComment: {
                    text: "",
                    user: "Guest"
                }
            }
        },

        mounted() {
            this.loadBook(this.$route.params.id);
        },

        methods: {
            loadBook(id) {
                this.loadingState = 'loading';
                client.getBookDetails(id)
                    .then(book => {
                        this.book = book;
                        this.loadingState = 'ok';
                    })
                    .catch(e => {
                        this.error = e.toString();
                        this.loadingState = 'error';
                        this.showErrorAlert = true;
                    });
            },

            deleteComment(id) {
                client.deleteComment(id)
                    .then(() => this.loadBook(this.$route.params.id))
                    .catch(e => {
                        this.error = e.toString();
                        this.showErrorAlert = true;
                    });
            },

            saveComment() {
                client.saveComment(
                    {
                        text: this.newComment.text,
                        user: this.newComment.user,
                        bookId: this.$route.params.id
                    }
                )
                    .then(() => {
                        this.loadBook(this.$route.params.id);
                        this.newComment.text = "";
                    })
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