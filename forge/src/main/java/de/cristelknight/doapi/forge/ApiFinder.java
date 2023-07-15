package de.cristelknight.doapi.forge;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.DoApi;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.List;

public class ApiFinder {

    public static <T> List<Pair<List<String>, T>> scanForAPIs(Class<?> annotationClazz, Class<T> returnClazz) {
        List<Pair<List<String>, T>> instances = Lists.newArrayList();
        for (ModFileScanData data : ModList.get().getAllScanData()) {
            List<ModFileScanData.AnnotationData> ebsTargets = data.getAnnotations().stream().
                    filter(annotationData -> Type.getType(annotationClazz).equals(annotationData.annotationType())).
                    toList();

            List<String> modIds = data.getIModInfoData().stream()
                    .flatMap(info -> info.getMods().stream())
                    .map(IModInfo::getModId)
                    .toList();

            for(ModFileScanData.AnnotationData ad : ebsTargets){
                Class<T> clazz;

                try {
                    clazz = (Class<T>) Class.forName(ad.memberName());
                } catch (ClassNotFoundException e) {
                    DoApi.LOGGER.error("Failed to load api class {} for @{} annotation", ad.clazz(), annotationClazz.getSimpleName(), e);
                    continue;
                }
                try {
                    instances.add(new Pair<>(modIds, clazz.getDeclaredConstructor().newInstance()));
                } catch (Throwable throwable) {
                    DoApi.LOGGER.error("Failed to load api: " + ad.memberName(), throwable);
                }
            }
        }
        return instances;
    }

}
