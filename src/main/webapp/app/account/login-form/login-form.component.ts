import axios from 'axios'
import Component from 'vue-class-component'
import { Inject, Vue } from 'vue-property-decorator'
import AccountService from '@/account/account.service'

@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'login-page')
    }
  }
})
export default class LoginForm extends Vue {
  public authenticationError = null
  public login = null
  public password = null
  public rememberMe: boolean = null
  @Inject('accountService')
  private accountService: () => AccountService

  public doLogin(): void {
    const data = { username: this.login, password: this.password, rememberMe: this.rememberMe }
    axios
      .post('api/authenticate', data)
      .then(result => {
        const bearerToken = result.headers.authorization
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length)
          if (this.rememberMe) {
            localStorage.setItem('esi-authenticationToken', jwt)
            sessionStorage.removeItem('esi-authenticationToken')
          } else {
            sessionStorage.setItem('esi-authenticationToken', jwt)
            localStorage.removeItem('esi-authenticationToken')
          }
        }
        this.authenticationError = false
        this.$root.$emit('bv::hide::modal', 'login-page')
        this.accountService().retrieveAccount()
      })
      .catch(() => {
        this.authenticationError = true
      })
  }
}
