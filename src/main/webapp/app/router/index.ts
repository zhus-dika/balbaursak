import Vue from 'vue';
import Component from 'vue-class-component';
Component.registerHooks([
  'beforeRouteEnter',
  'beforeRouteLeave',
  'beforeRouteUpdate', // for vue-router 2.2+
]);
import Router from 'vue-router';

const Home = () => import('@/main/produce/produce.vue');
const Error = () => import('@/core/error/error.vue');
const ProduceDetailsBucket = () => import('@/main/produce/produce-details.vue');
const RequestSend = () => import('@/main/request/request-update.vue');
const Bucket = () => import('@/main/request-points/requestpoint.vue');
const ProduceFeedbacks = () => import('@/main/feedback/feedback.vue');
const FeedbackCreateProduce = () => import('@/main/feedback/feedback-update.vue');

import account from '@/router/account.ts';
import admin from '@/router/admin.ts';
import entities from '@/router/entities.ts';
import pages from '@/router/pages.ts';
import { Authority } from '@/shared/security/authority';

Vue.use(Router);

// prettier-ignore
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/bucket',
      name: 'Bucket',
      component: Bucket
    },
    {
      path: '/feedback/produce',
      name: 'FeedbackCreateProduce',
      component: FeedbackCreateProduce
    },
    {
      path: '/produce/:produceId/feedbacks',
      name: 'ProduceFeedbacks',
      component: ProduceFeedbacks
    },
    {
      path: '/request/send',
      name: 'RequestSend',
      component: RequestSend
    },
    {
      path: '/produce/:produceId/view',
      name: 'ProduceDetailsBucket',
      component: ProduceDetailsBucket
    },
    {
      path: '/forbidden',
      name: 'Forbidden',
      component: Error,
      meta: { error403: true }
    },
    {
      path: '/not-found',
      name: 'NotFound',
      component: Error,
      meta: { error404: true }
    },
    ...account,
    ...admin,
    ...entities,
    ...pages
  ]
});
