/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FeedbackUpdateComponent from '@/entities/feedback/feedback-update.vue';
import FeedbackClass from '@/entities/feedback/feedback-update.component';
import FeedbackService from '@/entities/feedback/feedback.service';

import ProduceService from '@/entities/produce/produce.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Feedback Management Update Component', () => {
    let wrapper: Wrapper<FeedbackClass>;
    let comp: FeedbackClass;
    let feedbackServiceStub: SinonStubbedInstance<FeedbackService>;

    beforeEach(() => {
      feedbackServiceStub = sinon.createStubInstance<FeedbackService>(FeedbackService);

      wrapper = shallowMount<FeedbackClass>(FeedbackUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          feedbackService: () => feedbackServiceStub,

          produceService: () => new ProduceService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.feedback = entity;
        feedbackServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(feedbackServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.feedback = entity;
        feedbackServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(feedbackServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
