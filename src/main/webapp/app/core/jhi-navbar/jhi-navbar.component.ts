import { Component, Inject, Vue } from 'vue-property-decorator'
import LoginService from '@/account/login.service'
import AccountService from '@/account/account.service'

import EntitiesMenu from '@/entities/entities-menu.vue'

@Component({
  components: {
    'entities-menu': EntitiesMenu
  }
})
export default class JhiNavbar extends Vue {
  public version = 'v' + VERSION
  @Inject('loginService')
  private loginService: () => LoginService
  @Inject('accountService') private accountService: () => AccountService
  private currentLanguage = this.$store.getters.currentLanguage
  private languages: any = this.$store.getters.languages
  private hasAnyAuthorityValues = {}

  public get authenticated(): boolean {
    return this.$store.getters.authenticated
  }

  public get openAPIEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('api-docs') > -1
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1
  }

  created() {
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input]
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0 // current path starts with this path string
    })
  }

  public logout(): Promise<any> {
    localStorage.removeItem('esi-authenticationToken')
    sessionStorage.removeItem('esi-authenticationToken')
    this.$store.commit('logout')
    if (this.$route.path !== '/') {
      return this.$router.push('/')
    }
    return Promise.resolve(this.$router.currentRoute)
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root)
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        this.hasAnyAuthorityValues[authorities] = value
      })
    return this.hasAnyAuthorityValues[authorities] ?? false
  }
}