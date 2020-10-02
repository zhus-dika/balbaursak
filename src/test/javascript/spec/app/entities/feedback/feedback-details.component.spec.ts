/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FeedbackDetailComponent from '@/entities/feedback/feedback-details.vue';
import FeedbackClass from '@/entities/feedback/feedback-details.component';
import FeedbackService from '@/entities/feedback/feedback.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Feedback Management Detail Component', () => {
    let wrapper: Wrapper<FeedbackClass>;
    let comp: FeedbackClass;
    let feedbackServiceStub: SinonStubbedInstance<FeedbackService>;

    beforeEach(() => {
      feedbackServiceStub = sinon.createStubInstance<FeedbackService>(FeedbackService);

      wrapper = shallowMount<FeedbackClass>(FeedbackDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { feedbackService: () => feedbackServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFeedback = { id: 123 };
        feedbackServiceStub.find.resolves(foundFeedback);

        // WHEN
        comp.retrieveFeedback(123);
        await comp.$nextTick();

        // THEN
        expect(comp.feedback).toBe(foundFeedback);
      });
    });
  });
});
