import { Component, Inject, Vue } from 'vue-property-decorator'

import AlertService from '@/shared/alert/alert.service'

import BookRentingRequestService from '@/entities/book-renting-request/book-renting-request.service'
import { IBookRentingRequest } from '@/shared/model/book-renting-request.model'

import { Book, IBook } from '@/shared/model/book.model'
import BookService from './book.service'
import { BookStatus } from '@/shared/model/enumerations/book-status.model'
import { Category } from '@/shared/model/enumerations/category.model'
import { Language } from '@/shared/model/enumerations/language.model'

const validations: any = {
  book: {
    author: {},
    title: {},
    releaseDate: {},
    status: {},
    category: {},
    language: {}
  }
}

@Component({
  validations
})
export default class BookUpdate extends Vue {
  public book: IBook = new Book()
  public bookRentingRequests: IBookRentingRequest[] = []
  public bookStatusValues: string[] = Object.keys(BookStatus)
  public categoryValues: string[] = Object.keys(Category)
  public languageValues: string[] = Object.keys(Language)
  public isSaving = false
  public currentLanguage = ''
  @Inject('bookService') private bookService: () => BookService
  @Inject('alertService') private alertService: () => AlertService
  @Inject('bookRentingRequestService') private bookRentingRequestService: () => BookRentingRequestService

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.bookId) {
        vm.retrieveBook(to.params.bookId)
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
    if (this.book.id) {
      this.bookService()
        .update(this.book)
        .then(param => {
          this.isSaving = false
          this.$router.go(-1)
          const message = 'A Book is updated with identifier ' + param.id
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
      this.bookService()
        .create(this.book)
        .then(param => {
          this.isSaving = false
          this.$router.go(-1)
          const message = 'A Book is created with identifier ' + param.id
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

  public retrieveBook(bookId): void {
    this.bookService()
      .find(bookId)
      .then(res => {
        this.book = res
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response)
      })
  }

  public previousState(): void {
    this.$router.go(-1)
  }

  public initRelationships(): void {
    this.bookRentingRequestService()
      .retrieve()
      .then(res => {
        this.bookRentingRequests = res.data
      })
  }
}
