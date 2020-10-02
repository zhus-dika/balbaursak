/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RequestpointDetailComponent from '@/entities/requestpoint/requestpoint-details.vue';
import RequestpointClass from '@/entities/requestpoint/requestpoint-details.component';
import RequestpointService from '@/entities/requestpoint/requestpoint.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Requestpoint Management Detail Component', () => {
    let wrapper: Wrapper<RequestpointClass>;
    let comp: RequestpointClass;
    let requestpointServiceStub: SinonStubbedInstance<RequestpointService>;

    beforeEach(() => {
      requestpointServiceStub = sinon.createStubInstance<RequestpointService>(RequestpointService);

      wrapper = shallowMount<RequestpointClass>(RequestpointDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { requestpointService: () => requestpointServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRequestpoint = { id: 123 };
        requestpointServiceStub.find.resolves(foundRequestpoint);

        // WHEN
        comp.retrieveRequestpoint(123);
        await comp.$nextTick();

        // THEN
        expect(comp.requestpoint).toBe(foundRequestpoint);
      });
    });
  });
});
