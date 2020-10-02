/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RequestDetailComponent from '@/entities/request/request-details.vue';
import RequestClass from '@/entities/request/request-details.component';
import RequestService from '@/entities/request/request.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Request Management Detail Component', () => {
    let wrapper: Wrapper<RequestClass>;
    let comp: RequestClass;
    let requestServiceStub: SinonStubbedInstance<RequestService>;

    beforeEach(() => {
      requestServiceStub = sinon.createStubInstance<RequestService>(RequestService);

      wrapper = shallowMount<RequestClass>(RequestDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { requestService: () => requestServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRequest = { id: 123 };
        requestServiceStub.find.resolves(foundRequest);

        // WHEN
        comp.retrieveRequest(123);
        await comp.$nextTick();

        // THEN
        expect(comp.request).toBe(foundRequest);
      });
    });
  });
});
