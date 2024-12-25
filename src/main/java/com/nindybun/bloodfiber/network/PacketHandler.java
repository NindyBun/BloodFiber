package com.nindybun.bloodfiber.network;

import com.nindybun.bloodfiber.BloodFiber;
import com.nindybun.bloodfiber.network.packets.ClientOpenDeviceRadialMenu;
import com.nindybun.bloodfiber.network.packets.ServerOpenDeviceRadialMenu;
import com.nindybun.bloodfiber.network.packets.SetDeviceTool;
import net.neoforged.neoforge.network.bundle.PacketAndPayloadAcceptor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketHandler {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(BloodFiber.MODID);

        registrar.playToServer(ServerOpenDeviceRadialMenu.Data.TYPE,
                ServerOpenDeviceRadialMenu.Data.STREAM_CODEC,
                ServerOpenDeviceRadialMenu.get()::handle);

        registrar.playToServer(SetDeviceTool.Data.TYPE,
                SetDeviceTool.Data.STREAM_CODEC,
                SetDeviceTool.get()::handle);

        registrar.playToClient(ClientOpenDeviceRadialMenu.Data.TYPE,
                ClientOpenDeviceRadialMenu.Data.STREAM_CODEC,
                ClientOpenDeviceRadialMenu.get()::handle);
    }
}
