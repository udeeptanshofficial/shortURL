import API from './config';

class ShortUrlAPI {
    static create(payload) {
        return API.post('/createURL', payload);
    }

    static getById(id) {
        return API.get(`/urls/${id}`)
    }

    static verifySecurityKey(payload) {
        return API.post('/getSecureURL', payload);
    }
}

export default ShortUrlAPI;