/********************************************************************************
 * Copyright (c) 2022 Imixs Software Solutions GmbH and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package org.openbpmn.glsp;

import java.util.logging.Logger;

import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.core.model.SourceModelStorage;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.features.validation.ModelValidator;
import org.eclipse.glsp.server.gmodel.GModelCutOperationHandler;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.openbpmn.extensions.BPMNCreateExtensionHandler;
import org.openbpmn.extensions.BPMNElementExtension;
import org.openbpmn.extensions.BPMNModelExtension;
import org.openbpmn.extensions.elements.ConditionalEventDefinitionExtension;
import org.openbpmn.extensions.elements.DefaultBPMNDataObjectExtension;
import org.openbpmn.extensions.elements.DefaultBPMNDataStoreReferenceExtension;
import org.openbpmn.extensions.elements.DefaultBPMNDefinitionsExtension;
import org.openbpmn.extensions.elements.DefaultBPMNEdgeExtension;
import org.openbpmn.extensions.elements.DefaultBPMNEventExtension;
import org.openbpmn.extensions.elements.DefaultBPMNGatewayExtension;
import org.openbpmn.extensions.elements.DefaultBPMNMessageExtension;
import org.openbpmn.extensions.elements.DefaultBPMNParticipantExtension;
import org.openbpmn.extensions.elements.DefaultBPMNSequenceFlowExtension;
import org.openbpmn.extensions.elements.DefaultBPMNTaskExtension;
import org.openbpmn.extensions.elements.DefaultBPMNTextAnnotationExtension;
import org.openbpmn.extensions.elements.LinkEventDefinitionExtension;
import org.openbpmn.extensions.elements.MessageEventDefinitionExtension;
import org.openbpmn.extensions.elements.SignalEventDefinitionExtension;
import org.openbpmn.extensions.elements.TimerEventDefinitionExtension;
import org.openbpmn.extensions.model.FileLinkExtension;
import org.openbpmn.glsp.elements.data.BPMNCreateDataObjectHandler;
import org.openbpmn.glsp.elements.data.BPMNCreateDataStoreHandler;
import org.openbpmn.glsp.elements.data.BPMNCreateMessageHandler;
import org.openbpmn.glsp.elements.data.BPMNCreateTextAnnotationHandler;
import org.openbpmn.glsp.elements.edge.BPMNGEdgeCreateHandler;
import org.openbpmn.glsp.elements.event.BPMNCreateEventDefinitionHandler;
import org.openbpmn.glsp.elements.event.BPMNCreateEventHandler;
import org.openbpmn.glsp.elements.gateway.BPMNCreateGatewayHandler;
import org.openbpmn.glsp.elements.pool.CreateLaneHandler;
import org.openbpmn.glsp.elements.pool.CreatePoolHandler;
import org.openbpmn.glsp.elements.task.BPMNCreateTaskHandler;
import org.openbpmn.glsp.model.BPMNGModelFactory;
import org.openbpmn.glsp.model.BPMNGModelState;
import org.openbpmn.glsp.model.BPMNSourceModelStorage;
import org.openbpmn.glsp.operations.BPMNAutoAlignOperationHandler;
import org.openbpmn.glsp.operations.BPMNChangeBoundsOperationHandler;
import org.openbpmn.glsp.operations.BPMNChangeRoutingPointsOperationHandler;
import org.openbpmn.glsp.operations.BPMNClipboardDataActionHandler;
import org.openbpmn.glsp.operations.BPMNComputedBoundsActionHandler;
import org.openbpmn.glsp.operations.BPMNDeleteNodeHandler;
import org.openbpmn.glsp.operations.BPMNPasteOperationHandler;
import org.openbpmn.glsp.operations.BPMNPropertiesApplyOperationHandler;
import org.openbpmn.glsp.operations.BPMNReconnectEdgeOperationHandler;
import org.openbpmn.glsp.operations.BPMNResetRoutingOperationHandler;
import org.openbpmn.glsp.provider.BPMNCommandPaletteActionProvider;
import org.openbpmn.glsp.provider.BPMNToolPaletteItemProvider;
import org.openbpmn.glsp.validators.BPMNGLSPValidator;

import org.openbpmn.extensions.elements.CWP_BPMNTaskExtension;

import com.google.inject.multibindings.Multibinder;

/**
 * The DiagramModule contains the bindings in dedicated methods. Imixs BPMN
 * extends this module and customize it by overriding dedicated binding methods.
 *
 *
 * @author rsoika
 *
 */
public class CWP_Verify_DiagramModule extends BPMNDiagramModule {
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(CWP_Verify_DiagramModule.class.getName());

    

    

    @Override
    protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
        return BPMNToolPaletteItemProvider.class;
    }


    /**
     * This method adds the BPMN default element extensions
     * <p>
     * Overwrite this method to add custom BPMN Extensions
     *
     * @param binding
     */
	 @Override
    public void configureBPMNElementExtensions(final Multibinder<BPMNElementExtension> binding) {
        
		super.configureBPMNElementExtensions(binding);
		
		// new extension for CWP/verification work
		binding.addBinding().to(CWP_BPMNTaskExtension.class);

    }

    /**
     * This method adds the BPMN default model extensions
     * <p>
     * Overwrite this method to add custom BPMN Extensions
     *
     * @param binding
     */
	 @Override
    public void configureBPMNModelExtensions(final Multibinder<BPMNModelExtension> binding) {

		super.configureBPMNModelExtensions(binding);
        // bind BPMN default model extensions

    }
}
