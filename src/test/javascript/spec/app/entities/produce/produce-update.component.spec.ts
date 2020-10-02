/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProduceUpdateComponent from '@/entities/produce/produce-update.vue';
import ProduceClass from '@/entities/produce/produce-update.component';
import ProduceService from '@/entities/produce/produce.service';

import CategoryService from '@/entities/category/category.service';

import FeedbackService from '@/entities/feedback/feedback.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Produce Management Update Component', () => {
    let wrapper: Wrapper<ProduceClass>;
    let comp: ProduceClass;
    let produceServiceStub: SinonStubbedInstance<ProduceService>;

    beforeEach(() => {
      produceServiceStub = sinon.createStubInstance<ProduceService>(ProduceService);

      wrapper = shallowMount<ProduceClass>(ProduceUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          produceService: () => produceServiceStub,

          categoryService: () => new CategoryService(),

          feedbackService: () => new FeedbackService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.produce = entity;
        produceServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(produceServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.produce = entity;
        produceServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(produceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
