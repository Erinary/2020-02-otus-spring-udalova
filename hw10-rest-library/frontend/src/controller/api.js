import axios from 'axios';
import moment from "moment";

const httpClient = axios.create({
    baseURL: '/library/',
    timeout: 3000
})

export const client = {

    getBookList() {
        return httpClient.get('/book')
            .then(response => {
                return response.data;
            });
    },

    getBookDetails(id) {
        return httpClient.get(`/book/${id}`)
            .then(response => {
                return {
                    ...response.data,
                    comments: response.data.comments.map(comment => {
                        return {
                            ...comment,
                            date: moment(comment.date)
                        }
                    }),
                };
            });
    },

    saveBook({id, title, year, author, genre}) {
        let url, method;
        if (id !== null) {
            url = `/book/${id}`;
            method = 'put';
        } else {
            url = '/book';
            method = "post";
        }
        return httpClient.request({
            url: url,
            method: method,
            data: {
                id: id,
                title: title,
                year: year,
                author: author,
                genre: genre
            }
        })
            .then();
    },

    deleteBook(id) {
        return httpClient.delete(`/book/${id}`)
            .then();
    },

    saveComment({text, user, bookId}) {
        return httpClient.post('/comment', {
            text: text,
            user: user,
            bookId: bookId
        })
            .then();
    },

    deleteComment(id) {
        return httpClient.delete(`/comment/${id}`)
            .then();
    },

    getAuthorList() {
        return httpClient.get('/author')
            .then(response => {
                return response.data;
            });
    },

    getAuthor(id) {
        return httpClient.get(`/author/${id}`)
            .then(response => {
                return response.data;
            });
    },

    deleteAuthor(id) {
        return httpClient.delete(`/author/${id}`)
            .then();
    },

    getGenreList() {
        return httpClient.get('/genre')
            .then(response => {
                return response.data;
            });
    },

    getGenre(id) {
        return httpClient.get(`/genre/${id}`)
            .then(response => {
                return response.data;
            });
    },

    deleteGenre(id) {
        return httpClient.delete(`/genre/${id}`)
            .then();
    }
}