import { Authority } from '@/shared/security/authority'

const EsiUserManagementComponent = () => import('@/admin/user-management/user-management.vue')
const EsiUserManagementViewComponent = () => import('@/admin/user-management/user-management-view.vue')
const EsiUserManagementEditComponent = () => import('@/admin/user-management/user-management-edit.vue')
const EsiDocsComponent = () => import('@/admin/docs/docs.vue')

export default [
  {
    path: '/admin/user-management',
    name: 'EsiUser',
    component: EsiUserManagementComponent,
    meta: { authorities: [Authority.ADMIN] }
  },
  {
    path: '/admin/user-management/new',
    name: 'EsiUserCreate',
    component: EsiUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] }
  },
  {
    path: '/admin/user-management/:userId/edit',
    name: 'EsiUserEdit',
    component: EsiUserManagementEditComponent,
    meta: { authorities: [Authority.ADMIN] }
  },
  {
    path: '/admin/user-management/:userId/view',
    name: 'EsiUserView',
    component: EsiUserManagementViewComponent,
    meta: { authorities: [Authority.ADMIN] }
  },
  {
    path: '/admin/docs',
    name: 'EsiDocsComponent',
    component: EsiDocsComponent,
    meta: { authorities: [Authority.ADMIN] }
  }
]
