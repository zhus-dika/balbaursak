import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Feedback = () => import('@/entities/feedback/feedback.vue');
// prettier-ignore
const FeedbackUpdate = () => import('@/entities/feedback/feedback-update.vue');
// prettier-ignore
const FeedbackDetails = () => import('@/entities/feedback/feedback-details.vue');
// prettier-ignore
const Product = () => import('@/entities/product/product.vue');
// prettier-ignore
const ProductUpdate = () => import('@/entities/product/product-update.vue');
// prettier-ignore
const ProductDetails = () => import('@/entities/product/product-details.vue');
// prettier-ignore
const Purchase = () => import('@/entities/purchase/purchase.vue');
// prettier-ignore
const PurchaseUpdate = () => import('@/entities/purchase/purchase-update.vue');
// prettier-ignore
const PurchaseDetails = () => import('@/entities/purchase/purchase-details.vue');
// prettier-ignore
const Produce = () => import('@/entities/produce/produce.vue');
// prettier-ignore
const ProduceUpdate = () => import('@/entities/produce/produce-update.vue');
// prettier-ignore
const ProduceDetails = () => import('@/entities/produce/produce-details.vue');
// prettier-ignore
const Requestpoint = () => import('@/entities/requestpoint/requestpoint.vue');

const RequestPoints = () => import('@/main/requestpoint/requestpoint.vue');
// prettier-ignore
const RequestpointUpdate = () => import('@/entities/requestpoint/requestpoint-update.vue');
// prettier-ignore
const RequestpointDetails = () => import('@/entities/requestpoint/requestpoint-details.vue');
// prettier-ignore
const Request = () => import('@/entities/request/request.vue');
// prettier-ignore
const RequestUpdate = () => import('@/entities/request/request-update.vue');
// prettier-ignore
const RequestDetails = () => import('@/main/request/request-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/feedback',
    name: 'Feedback',
    component: Feedback,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feedback/new',
    name: 'FeedbackCreate',
    component: FeedbackUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feedback/:feedbackId/edit',
    name: 'FeedbackEdit',
    component: FeedbackUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feedback/:feedbackId/view',
    name: 'FeedbackView',
    component: FeedbackDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/product',
    name: 'Product',
    component: Product,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/product/new',
    name: 'ProductCreate',
    component: ProductUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/product/:productId/edit',
    name: 'ProductEdit',
    component: ProductUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/product/:productId/view',
    name: 'ProductView',
    component: ProductDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/purchase',
    name: 'Purchase',
    component: Purchase,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/purchase/new',
    name: 'PurchaseCreate',
    component: PurchaseUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/purchase/:purchaseId/edit',
    name: 'PurchaseEdit',
    component: PurchaseUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/purchase/:purchaseId/view',
    name: 'PurchaseView',
    component: PurchaseDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/produce',
    name: 'Produce',
    component: Produce,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/produce/new',
    name: 'ProduceCreate',
    component: ProduceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/produce/:produceId/edit',
    name: 'ProduceEdit',
    component: ProduceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/produce/:produceId/view',
    name: 'ProduceView',
    component: ProduceDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/requestpoint',
    name: 'Requestpoint',
    component: Requestpoint,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request/:requestId/points',
    name: 'RequestGetPoints',
    component: RequestPoints,
    meta: { authorities: [Authority.USER] }
  },
  {
    path: '/requestpoint/new',
    name: 'RequestpointCreate',
    component: RequestpointUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/requestpoint/:requestpointId/edit',
    name: 'RequestpointEdit',
    component: RequestpointUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/requestpoint/:requestpointId/view',
    name: 'RequestpointView',
    component: RequestpointDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/request',
    name: 'Request',
    component: Request,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request/new',
    name: 'RequestCreate',
    component: RequestUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request/:requestId/edit',
    name: 'RequestEdit',
    component: RequestUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/request/:requestId/view',
    name: 'RequestView',
    component: RequestDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/category',
    name: 'Category',
    component: Category,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/new',
    name: 'CategoryCreate',
    component: CategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/:categoryId/edit',
    name: 'CategoryEdit',
    component: CategoryUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/category/:categoryId/view',
    name: 'CategoryView',
    component: CategoryDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/feedback',
    name: 'Feedback',
    component: Feedback,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feedback/new',
    name: 'FeedbackCreate',
    component: FeedbackUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feedback/:feedbackId/edit',
    name: 'FeedbackEdit',
    component: FeedbackUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/feedback/:feedbackId/view',
    name: 'FeedbackView',
    component: FeedbackDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/requestpoint',
    name: 'Requestpoint',
    component: Requestpoint,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/requestpoint/new',
    name: 'RequestpointCreate',
    component: RequestpointUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/requestpoint/:requestpointId/edit',
    name: 'RequestpointEdit',
    component: RequestpointUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/requestpoint/:requestpointId/view',
    name: 'RequestpointView',
    component: RequestpointDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
