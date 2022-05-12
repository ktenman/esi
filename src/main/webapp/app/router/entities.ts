import { Authority } from '@/shared/security/authority'
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue')

// prettier-ignore
const Book = () => import('@/entities/book/book.vue')
// prettier-ignore
const BookUpdate = () => import('@/entities/book/book-update.vue')
// prettier-ignore
const BookDetails = () => import('@/entities/book/book-details.vue')
// prettier-ignore
const BookRentingRequest = () => import('@/entities/book-renting-request/book-renting-request.vue')
// prettier-ignore
const BookRentingRequestUpdate = () => import('@/entities/book-renting-request/book-renting-request-update.vue')
// prettier-ignore
const BookRentingRequestDetails = () => import('@/entities/book-renting-request/book-renting-request-details.vue')
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'book',
      name: 'Book',
      component: Book,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book/new',
      name: 'BookCreate',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book/:bookId/edit',
      name: 'BookEdit',
      component: BookUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book/:bookId/view',
      name: 'BookView',
      component: BookDetails,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book-renting-request',
      name: 'BookRentingRequest',
      component: BookRentingRequest,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book-renting-request/new',
      name: 'BookRentingRequestCreate',
      component: BookRentingRequestUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book-renting-request/:bookRentingRequestId/edit',
      name: 'BookRentingRequestEdit',
      component: BookRentingRequestUpdate,
      meta: { authorities: [Authority.USER] }
    },
    {
      path: 'book-renting-request/:bookRentingRequestId/view',
      name: 'BookRentingRequestView',
      component: BookRentingRequestDetails,
      meta: { authorities: [Authority.USER] }
    }
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ]
}
