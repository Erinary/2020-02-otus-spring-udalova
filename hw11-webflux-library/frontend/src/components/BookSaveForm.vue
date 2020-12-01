<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h2>Book info
                    <b-spinner v-if="loadingState === 'loading'" class="ml-2"/>
                </h2>
            </b-col>
        </b-row>
        <b-row>
            <b-col>
                <b-alert v-model="showErrorAlert" dismissible variant="danger">{{error}}</b-alert>
            </b-col>
        </b-row>
        <b-row v-if="loadingState === 'ok'">
            <b-col class="col-6">
                <b-form @submit="saveBook()" @reset="back()">
                    <b-form-group label="Title" label-for="bookTitle">
                        <b-form-input id="bookTitle" type="text" v-model="book.title"></b-form-input>
                    </b-form-group>
                    <b-form-group label="Year" label-for="bookYear">
                        <b-form-input id="bookYear" type="number" v-model="book.year"></b-form-input>
                    </b-form-group>
                    <b-form-group label="Author" label-for="bookAuthor">
                        <b-form-input id="bookAuthor" type="text" v-model="book.author"></b-form-input>
                    </b-form-group>
                    <b-form-group label="Genre" label-for="bookGenre">
                        <b-form-input id="bookGenre" type="text" v-model="book.genre"></b-form-input>
                    </b-form-group>
                    <div>
                        <b-button type="submit" class="btn btn-info btn-sm">Save</b-button>
                        <b-button type="reset" class="btn btn-info btn-sm ml-2">Cancel</b-button>
                    </div>
                </b-form>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    import {client} from '../controller/api.js';

    export default {
        name: "BookSaveForm",

        data() {
            return {
                showErrorAlert: false,
                loadingState: "ok",
                error: "",
                book: {
                    title: "Title",
                    year: 1970,
                    author: "Author",
                    genre: "Genre"
                }
            }
        },

        mounted() {
            const bookId = this.getBookIdFromRoute()
            if (bookId !== null) {
                this.loadBook(bookId);
            }
        },

        methods: {
            getBookIdFromRoute() {
                return (typeof this.$route.params.id !== 'undefined') ? this.$route.params.id : null;
            },

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

            saveBook() {
                const id = this.getBookIdFromRoute();
                client.saveBook({
                    id: id,
                    title: this.book.title,
                    year: this.book.year,
                    author: this.book.author,
                    genre: this.book.genre
                })
                    .then(() => {
                        if (id !== null) {
                            this.$router.push({name: 'book-details', params: {id: id}});
                        } else {
                            this.$router.push({name: 'books'});
                        }
                    })
                    .catch(e => {
                        this.error = e.toString();
                        this.showErrorAlert = true;
                    });
            },

            back() {
                history.back();
            }
        }
    }
</script>

<style scoped>

</style>