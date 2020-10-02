/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RequestpointUpdateComponent from '@/entities/requestpoint/requestpoint-update.vue';
import RequestpointClass from '@/entities/requestpoint/requestpoint-update.component';
import RequestpointService from '@/entities/requestpoint/requestpoint.service';

import ProduceService from '@/entities/produce/produce.service';

import RequestService from '@/entities/request/request.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Requestpoint Management Update Component', () => {
    let wrapper: Wrapper<RequestpointClass>;
    let comp: RequestpointClass;
    let requestpointServiceStub: SinonStubbedInstance<RequestpointService>;

    beforeEach(() => {
      requestpointServiceStub = sinon.createStubInstance<RequestpointService>(RequestpointService);

      wrapper = shallowMount<RequestpointClass>(RequestpointUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          requestpointService: () => requestpointServiceStub,

          produceService: () => new ProduceService(),

          requestService: () => new RequestService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.requestpoint = entity;
        requestpointServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requestpointServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.requestpoint = entity;
        requestpointServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(requestpointServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
