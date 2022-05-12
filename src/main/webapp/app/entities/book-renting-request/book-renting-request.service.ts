import axios from 'axios'

import buildPaginationQueryOpts from '@/shared/sort/sorts'

import { IBookRentingRequest } from '@/shared/model/book-renting-request.model'

const baseApiUrl = 'api/book-renting-requests'

export default class BookRentingRequestService {
  public find(id: number): Promise<IBookRentingRequest> {
    return new Promise<IBookRentingRequest>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public create(entity: IBookRentingRequest): Promise<IBookRentingRequest> {
    return new Promise<IBookRentingRequest>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public update(entity: IBookRentingRequest): Promise<IBookRentingRequest> {
    return new Promise<IBookRentingRequest>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }

  public partialUpdate(entity: IBookRentingRequest): Promise<IBookRentingRequest> {
    return new Promise<IBookRentingRequest>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data)
        })
        .catch(err => {
          reject(err)
        })
    })
  }
}
