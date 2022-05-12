import { Component, Inject, Vue } from 'vue-property-decorator'

import { IBookRentingRequest } from '@/shared/model/book-renting-request.model'
import BookRentingRequestService from './book-renting-request.service'
import AlertService from '@/shared/alert/alert.service'

@Component
export default class BookRentingRequestDetails extends Vue {
  public bookRentingRequest: IBookRentingRequest = {}
  @Inject('bookRentingRequestService') private bookRentingRequestService: () => BookRentingRequestService
  @Inject('alertService') private alertService: () => AlertService

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bookRentingRequestId) {
        vm.retrieveBookRentingRequest(to.params.bookRentingRequestId)
      }
    })
  }

  public retrieveBookRentingRequest(bookRentingRequestId) {
    this.bookRentingRequestService()
      .find(bookRentingRequestId)
      .then(res => {
        this.bookRentingRequest = res
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response)
      })
  }

  public previousState() {
    this.$router.go(-1)
  }
}
