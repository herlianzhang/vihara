package pro.herlian.vihara.di

import dagger.Module
import pro.herlian.vihara.feature.auth.di.AuthComponent

@Module(subcomponents = [AuthComponent::class])
class SubcomponentsModule