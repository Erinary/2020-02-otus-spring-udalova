import axios from 'axios';

const httpClient = axios.create({
    baseURL: '/library/',
    timeout: 3000
})

export const client = {

    getAuthorList() {
        return httpClient.get('/author')
            .then(response => {
                return response.data;
            });
    },

    deleteAuthor(id) {
        return httpClient.delete(`/author/${id}`)
            .then();
    }
}