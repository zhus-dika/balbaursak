<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="balbaursakApp.produce.home.createOrEditLabel" v-text="$t('balbaursakApp.produce.home.createOrEditLabel')">Create or edit a Produce</h2>
                <div>
                    <div class="form-group" v-if="produce.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="produce.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.name')" for="produce-name">Name</label>
                        <input type="text" class="form-control" name="name" id="produce-name"
                            :class="{'valid': !$v.produce.name.$invalid, 'invalid': $v.produce.name.$invalid }" v-model="$v.produce.name.$model"  required/>
                        <div v-if="$v.produce.name.$anyDirty && $v.produce.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.unit')" for="produce-unit">Unit</label>
                        <input type="text" class="form-control" name="unit" id="produce-unit"
                            :class="{'valid': !$v.produce.unit.$invalid, 'invalid': $v.produce.unit.$invalid }" v-model="$v.produce.unit.$model"  required/>
                        <div v-if="$v.produce.unit.$anyDirty && $v.produce.unit.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.unit.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.contains')" for="produce-contains">Contains</label>
                        <input type="text" class="form-control" name="contains" id="produce-contains"
                            :class="{'valid': !$v.produce.contains.$invalid, 'invalid': $v.produce.contains.$invalid }" v-model="$v.produce.contains.$model"  required/>
                        <div v-if="$v.produce.contains.$anyDirty && $v.produce.contains.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.contains.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.produce.contains.maxLength" v-text="$t('entity.validation.maxlength', {max: 1024})">
                                This field cannot be longer than 1024 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.days')" for="produce-days">Days</label>
                        <input type="number" class="form-control" name="days" id="produce-days"
                            :class="{'valid': !$v.produce.days.$invalid, 'invalid': $v.produce.days.$invalid }" v-model.number="$v.produce.days.$model"  required/>
                        <div v-if="$v.produce.days.$anyDirty && $v.produce.days.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.days.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.produce.days.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.produce.days.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.description')" for="produce-description">Description</label>
                        <input type="text" class="form-control" name="description" id="produce-description"
                            :class="{'valid': !$v.produce.description.$invalid, 'invalid': $v.produce.description.$invalid }" v-model="$v.produce.description.$model"  required/>
                        <div v-if="$v.produce.description.$anyDirty && $v.produce.description.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.description.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.produce.description.maxLength" v-text="$t('entity.validation.maxlength', {max: 1024})">
                                This field cannot be longer than 1024 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.price')" for="produce-price">Price</label>
                        <input type="number" class="form-control" name="price" id="produce-price"
                            :class="{'valid': !$v.produce.price.$invalid, 'invalid': $v.produce.price.$invalid }" v-model.number="$v.produce.price.$model"  required/>
                        <div v-if="$v.produce.price.$anyDirty && $v.produce.price.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.price.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.produce.price.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.produce.price.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.file')" for="produce-file">File</label>
                        <div>
                            <img v-bind:src="'data:' + produce.fileContentType + ';base64,' + produce.file" style="max-height: 100px;" v-if="produce.file" alt="produce image"/>
                            <div v-if="produce.file" class="form-text text-danger clearfix">
                                <span class="pull-left">{{produce.fileContentType}}, {{byteSize(produce.file)}}</span>
                                <button type="button" v-on:click="clearInputImage('file', 'fileContentType', 'file_file')" class="btn btn-secondary btn-xs pull-right">
                                    <font-awesome-icon icon="times"></font-awesome-icon>
                                </button>
                            </div>
                            <input type="file" ref="file_file" id="file_file" v-on:change="setFileData($event, produce, 'file', true)" accept="image/*" v-text="$t('entity.action.addimage')"/>
                        </div>
                        <input type="hidden" class="form-control" name="file" id="produce-file"
                            :class="{'valid': !$v.produce.file.$invalid, 'invalid': $v.produce.file.$invalid }" v-model="$v.produce.file.$model"  required/>
                        <input type="hidden" class="form-control" name="fileContentType" id="produce-fileContentType"
                            v-model="produce.fileContentType" />
                        <div v-if="$v.produce.file.$anyDirty && $v.produce.file.$invalid">
                            <small class="form-text text-danger" v-if="!$v.produce.file.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.produce.category')" for="produce-category">Category</label>
                        <select class="form-control" id="produce-category" name="category" v-model="produce.category">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="produce.category && categoryOption.id === produce.category.id ? produce.category : categoryOption" v-for="categoryOption in categories" :key="categoryOption.id">{{categoryOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.produce.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./produce-update.component.ts">
</script>
