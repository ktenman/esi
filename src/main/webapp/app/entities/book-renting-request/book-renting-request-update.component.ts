import { Component, Inject, Vue } from 'vue-property-decorator'

import dayjs from 'dayjs'
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters'

import AlertService from '@/shared/alert/alert.service'

import BookService from '@/entities/book/book.service'
import { IBook } from '@/shared/model/book.model'

import { BookRentingRequest, IBookRentingRequest } from '@/shared/model/book-renting-request.model'
import BookRentingRequestService from './book-renting-request.service'
import { RentingStatus } from '@/shared/model/enumerations/renting-status.model'

const validations: any = {
  bookRentingRequest: {
    status: {},
    rentedAt: {},
    rentedUntil: {}
  }
}

@Component({
  validations
})
export default class BookRentingRequestUpdate extends Vue {
  public bookRentingRequest: IBookRentingRequest = new BookRentingRequest()
  public books: IBook[] = []
  public rentingStatusValues: string[] = Object.keys(RentingStatus)
  public isSaving = false
  public currentLanguage = ''
  @Inject('bookRentingRequestService') private bookRentingRequestService: () => BookRentingRequestService
  @Inject('alertService') private alertService: () => AlertService
  @Inject('bookService') private bookService: () => BookService

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bookRentingRequestId) {
        vm.retrieveBookRentingRequest(to.params.bookRentingRequestId)
      }
      vm.initRelationships()
    })
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage
      }
    )
  }

  public save(): void {
    this.isSaving = true
    if (this.bookRentingRequest.id) {
      this.bookRentingRequestService()
        .update(this.bookRentingRequest)
        .then(param => {
          this.isSaving = false
          this.$router.go(-1)
          const message = 'A BookRentingRequest is updated with identifier ' + param.id
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000
          })
        })
        .catch(error => {
          this.isSaving = false
          this.alertService().showHttpError(this, error.response)
        })
    } else {
      this.bookRentingRequestService()
        .create(this.bookRentingRequest)
        .then(param => {
          this.isSaving = false
          this.$router.go(-1)
          const message = 'A BookRentingRequest is created with identifier ' + param.id
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000
          })
        })
        .catch(error => {
          this.isSaving = false
          this.alertService().showHttpError(this, error.response)
        })
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT)
    }
    return null
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.bookRentingRequest[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT)
    } else {
      this.bookRentingRequest[field] = null
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.bookRentingRequest[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT)
    } else {
      this.bookRentingRequest[field] = null
    }
  }

  public retrieveBookRentingRequest(bookRentingRequestId): void {
    this.bookRentingRequestService()
      .find(bookRentingRequestId)
      .then(res => {
        res.rentedAt = new Date(res.rentedAt)
        res.rentedUntil = new Date(res.rentedUntil)
        this.bookRentingRequest = res
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response)
      })
  }

  public previousState(): void {
    this.$router.go(-1)
  }

  public initRelationships(): void {
    this.bookService()
      .retrieve()
      .then(res => {
        this.books = res.data
      })
  }
}
